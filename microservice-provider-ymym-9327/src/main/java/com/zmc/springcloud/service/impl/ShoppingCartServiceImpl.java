package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.express.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeingClient;
import com.zmc.springcloud.feignclient.product.SpecialtyImageFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyPriceFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionFeignClient;
import com.zmc.springcloud.feignclient.promotion.HyPromotionPicFeignClient;
import com.zmc.springcloud.feignclient.promotion.HySingleitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.mapper.ShoppingCartMapper;
import com.zmc.springcloud.service.ShoppingCartService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private WechatAccountFeignClient wechatAccountFeignClient;

    @Autowired
    private HyGroupitemPromotionFeignClient hyGroupitemPromotionFeignClient;

    @Autowired
    private HyPromotionFeignClient hyPromotionFeignClient;

    @Autowired
    private HyPromotionPicFeignClient hyPromotionPicFeignClient;

    @Autowired
    private SpecialtySpecificationFeignClient specialtySpecificationFeignClient;

    @Autowired
    private SpecialtyPriceFeignClient specialtyPriceFeignClient;

    @Autowired
    private HySingleitemPromotionFeignClient hySingleitemPromotionFeignClient;

    @Autowired
    private SpecialtyFeingClient specialtyFeingClient;

    @Autowired
    private SpecialtyImageFeignClient specialtyImageFeignClient;

    @Override
    public void addShoppingCart(ShoppingCart shoppingCart, Long wechatId) throws Exception {
        WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(wechatId);
        if(wechatAccount == null){
            throw new Exception("用户不存在");
        }
        List<ShoppingCart> shoppingCartList;
        if(!shoppingCart.getIsGroupPromotion()){
            shoppingCartList = shoppingCartMapper.getListShoppingCart(wechatId, shoppingCart.getSpecialtyId(), null);
        }else{
            shoppingCartList = shoppingCartMapper.getListShoppingCart(wechatId, shoppingCart.getSpecialtyId(), shoppingCart.getSpecialtySpecificationId());
        }
        if(shoppingCartList == null || shoppingCartList.isEmpty()){
           shoppingCart.setWechatId(wechatId);
           shoppingCartMapper.save(shoppingCart);
        }else{
            for(ShoppingCart tmp:shoppingCartList){
                if(tmp==null){
                    continue;
                }
                int old=tmp.getQuantity();
                if(shoppingCart.getQuantity()!=null){
                    tmp.setQuantity(old+shoppingCart.getQuantity());
                }else{
                    tmp.setQuantity(old+1);
                }
                shoppingCartMapper.updateQuantity(tmp);
            }
        }
    }

    @Override
    public Json getShoppingCartList(Long wechat_id) {
        Json j = new Json();
        WechatAccount wechatAccount = wechatAccountFeignClient.getWechatAccountById(wechat_id);
        if(wechatAccount == null){
            j.setSuccess(false);
            j.setMsg("用户不存在");
            return j;
        }
        List<HashMap<String, Object>> result = new LinkedList<>();
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.getListShoppingCart(wechat_id, null, null);
        for (ShoppingCart tmp : shoppingCarts) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", tmp.getId());
            map.put("specialtyId", tmp.getSpecialtyId());
            map.put("specialtySpecificationId", tmp.getSpecialtySpecificationId());
            map.put("quantity", tmp.getQuantity());
            map.put("isGroupPromotion", tmp.getIsGroupPromotion());
            Boolean isGroupPromotion = tmp.getIsGroupPromotion();
            // 如果是组合产品
            if(isGroupPromotion){
                // 获取价格
                HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(tmp.getSpecialtyId());
                map.put("curPrice", groupitemPromotion.getSellPrice());
                // 获取优惠活动id
                Long promotionId = groupitemPromotion.getPromotionId();
                map.put("promotionId", promotionId);
                HyPromotion hyPromotion = hyPromotionFeignClient.getHyPromotionById(promotionId);
                map.put("name", hyPromotion.getPromotionName());
                map.put("specification", null);

                HyPromotionPic hyPromotionPic = null;
                List<HyPromotionPic> hyPromotionPics = hyPromotionPicFeignClient.getHyPromotionPicListByPromotionId(promotionId);
                for (HyPromotionPic h : hyPromotionPics) {
                    if(h.getIsTag()){
                        hyPromotionPic = h;
                        break;
                    }
                }
                map.put("iconURL", hyPromotionPic);
            }else{
                // 如果是普通产品
                // 去价格变化表里面查
                SpecialtySpecification specialtySpecification = specialtySpecificationFeignClient.getSpecialtySpecificationById(tmp.getSpecialtySpecificationId());
                SpecialtyPrice specialtyPrice = specialtyPriceFeignClient.getSpecialtyPriceList(tmp.getSpecialtySpecificationId(), true);
                if(specialtyPrice != null){
                    map.put("curPrice", specialtyPrice.getPlatformPrice());
                }else{
                    j.setSuccess(false);
                    j.setMsg("数据异常，没有有效价格信息");
                    return j;
                }

                // 获取优惠活动id
                // 获取有效优惠明细
                HySingleitemPromotion singleitemPromotion = hySingleitemPromotionFeignClient.getValidSingleitemPromotion(specialtySpecification.getId(), null);
                // 如果没有优惠, 则加入"0L"promotionItems中
                if(singleitemPromotion != null){
                    map.put("promotionId", singleitemPromotion.getPromotionId());
                }
                Specialty specialty = specialtyFeingClient.getSpecialtyById(tmp.getSpecialtyId());
                map.put("name", specialty.getName());
                map.put("specification", specialtySpecification.getSpecification());

                SpecialtyImage si = null;
                List<SpecialtyImage> ssImages = specialtyImageFeignClient.getSpecialtyImageListBySpecialtyId(specialty.getId());
                for (SpecialtyImage s : ssImages) {
                    if (s != null && s.getIsLogo() != null && s.getIsLogo() == true) {
                        si = s;
                        break;
                    }
                }
                map.put("iconURL", si);
            }
            result.add(map);
        }
        j.setSuccess(true);
        j.setMsg("操作成功");
        j.setObj(result);
        return j;
    }

    @Override
    public void delete(Long id) {
        shoppingCartMapper.delete(id);
    }
}
