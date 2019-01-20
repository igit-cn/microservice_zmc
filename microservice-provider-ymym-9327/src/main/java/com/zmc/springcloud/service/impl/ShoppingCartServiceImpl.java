package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.*;
import com.zmc.springcloud.feignclient.express.SpecialtySpecificationFeignClient;
import com.zmc.springcloud.feignclient.order.PurchaseItemFeignClient;
import com.zmc.springcloud.feignclient.product.HyGroupitemPromotionFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyFeingClient;
import com.zmc.springcloud.feignclient.product.SpecialtyImageFeignClient;
import com.zmc.springcloud.feignclient.product.SpecialtyPriceFeignClient;
import com.zmc.springcloud.feignclient.promotion.*;
import com.zmc.springcloud.feignclient.wechataccount.VipLevelFeignClient;
import com.zmc.springcloud.feignclient.wechataccount.WechatAccountFeignClient;
import com.zmc.springcloud.mapper.ShoppingCartMapper;
import com.zmc.springcloud.service.ShoppingCartService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private PurchaseItemFeignClient purchaseItemFeignClient;

    @Autowired
    private VipLevelFeignClient vipLevelFeignClient;

    @Autowired
    private HyFullDiscountFeignClient hyFullDiscountFeignClient;

    @Autowired
    private HyFullPresentFeignClient hyFullPresentFeignClient;

    @Autowired
    private HyFullSubstractFeignClient hyFullSubstractFeignClient;

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
                SpecialtyPrice specialtyPrice = specialtyPriceFeignClient.getSpecialtyPrice(tmp.getSpecialtySpecificationId(), true);
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

    @Override
    public void updateQuantity(ShoppingCart shoppingCart) {
        shoppingCartMapper.updateQuantity(shoppingCart);
    }

    @Override
    public HashMap<String, Object> totalPrice(List<HashMap<String, Object>> params, List<HashMap<String, Object>> bodys, Long wechat_id) {
        List<HashMap<String, Object>> customItems = null;
        if (params != null) {
            customItems = params;
        }
        if (bodys != null) {
            customItems = bodys;
        }
        Map<Long, ArrayList<HashMap<String, Object>>> promotionItems = getPromotionItems(customItems);
        List<Map<String, Object>> customPromotions = new ArrayList<>();
        for (Map.Entry<Long, ArrayList<HashMap<String, Object>>> promotionItem : promotionItems.entrySet()) {
            Map<String, Object> customPromotion = new HashMap<>();
            Long promotionId = promotionItem.getKey();
            ArrayList<HashMap<String, Object>> promotionCustomItems = promotionItem.getValue();
            // 得到活动id
            customPromotion.put("promotionId", promotionId);
            // 得到活动下属所有商品
            customPromotion.put("promotionItems", promotionCustomItems);
            BigDecimal totalPrice = BigDecimal.ZERO;
            // 获取活动商品总价
            for (HashMap<String, Object> promotionCustomItem : promotionCustomItems) {
                BigDecimal curPrice = (BigDecimal.valueOf(
                        promotionCustomItem.get("curPrice").getClass().equals(Integer.class) ?
                                ((Integer) promotionCustomItem.get("curPrice")).longValue() :
                                ((Double) promotionCustomItem.get("curPrice"))));
                Integer quantity = (Integer) promotionCustomItem.get("quantity");
                totalPrice = totalPrice.add(curPrice.multiply(BigDecimal.valueOf(quantity)));
            }
            customPromotion.put("totalMoney", totalPrice);

            if (promotionId.equals(0L)) {
                //每月18日判断用户会员等级，看普通产品是否打折
                Calendar cal = Calendar.getInstance();
                Viplevel viplevel = vipLevelFeignClient.getViplevelBywechataccountId(wechat_id);
                if (cal.get(Calendar.DATE) == 18 && viplevel != null) {
                    BigDecimal discount = viplevel.getDiscount();
                    customPromotion.put("promotion", "会员18日折扣");
                    customPromotion.put("promotionMoney", totalPrice.subtract(totalPrice.multiply(discount)));
                    customPromotion.put("promotionCondition", discount);
                    customPromotion.put("finalMoney", totalPrice.multiply(discount));
                } else {
                    customPromotion.put("promotion", null);
                    customPromotion.put("promotionMoney", BigDecimal.ZERO);
                    customPromotion.put("promotionCondition", null);
                    customPromotion.put("finalMoney", totalPrice);
                }
            } else {
                HyPromotion promotion = hyPromotionFeignClient.getHyPromotionById(promotionId);
                HyPromotion.PromotionRule promotionRule = promotion.getPromotionRule();
                switch (promotionRule) {
                    case 满减: {
                        List<HyFullSubstract> hyFullSubstracts = hyFullSubstractFeignClient.getHyFullSubstractListByPromotionId(promotionId);
                        HyFullSubstract hyFullSubstract = null;
                        for (HyFullSubstract h : hyFullSubstracts) {
                            if (totalPrice.compareTo(h.getFullFreeRequirement()) < 0) {
                                break;
                            }
                            hyFullSubstract = h;
                        }
                        if (hyFullSubstract == null) {
                            customPromotion.put("promotionCondition", hyFullSubstract);
                            customPromotion.put("promotionMoney", BigDecimal.ZERO);
                            customPromotion.put("finalMoney", totalPrice);
                        } else {
                            customPromotion.put("promotionCondition", hyFullSubstract);
                            customPromotion.put("promotionMoney", hyFullSubstract.getFullFreeAmount());
                            customPromotion.put("finalMoney", totalPrice.subtract(hyFullSubstract.getFullFreeAmount()));
                        }
                    }
                    break;
                    case 满折: {
                        List<HyFullDiscount> hyFullDiscounts = hyFullDiscountFeignClient.getHyFullDiscountListByPromotionId(promotionId);
                        HyFullDiscount hyFullDiscount = null;
                        for (HyFullDiscount h : hyFullDiscounts) {
                            if (totalPrice.compareTo(h.getDiscountRequirenment()) < 0) {
                                break;
                            }
                            hyFullDiscount = h;
                        }
                        if (hyFullDiscount == null) {
                            customPromotion.put("promotionCondition", hyFullDiscount);
                            customPromotion.put("promotionMoney", BigDecimal.ZERO);
                            customPromotion.put("finalMoney", totalPrice);
                        } else {
                            customPromotion.put("promotionCondition", hyFullDiscount);
                            customPromotion.put("finalMoney", totalPrice.multiply(hyFullDiscount.getDiscountOff()));
                            customPromotion.put("promotionMoney",
                                    totalPrice.subtract((BigDecimal) customPromotion.get("finalMoney")));
                        }
                    }
                    break;
                    case 满赠: {
                        List<HyFullPresent> hyFullPresents = hyFullPresentFeignClient.getHyFullPresentListByPromotionId(promotionId);
                        List<HyFullPresent> hyFullPresentList = new ArrayList<>();
                        for (HyFullPresent h : hyFullPresents) {
                            if (totalPrice.compareTo(h.getFullPresentRequirenment()) < 0) {
                                break;
                            }

                            if (!hyFullPresentList.isEmpty() && hyFullPresentList.get(0).getFullPresentRequirenment()
                                    .compareTo(h.getFullPresentRequirenment()) < 0) {
                                hyFullPresentList.clear();
                            }
                            hyFullPresentList.add(h);
                        }
                        customPromotion.put("promotionCondition", hyFullPresentList);
                        customPromotion.put("promotionMoney", BigDecimal.ZERO);
                        customPromotion.put("finalMoney", totalPrice);
                    }
                    break;
                    default:
                        break;
                }
            }
            customPromotions.add(customPromotion);
        }

        HashMap<String, Object> obj = new HashMap<>();
        BigDecimal totalMoney = BigDecimal.ZERO;
        BigDecimal promotionMoney = BigDecimal.ZERO;
        BigDecimal finalMoney = BigDecimal.ZERO;
        for (Map<String, Object> customPromotion : customPromotions) {
            totalMoney = totalMoney.add((BigDecimal) customPromotion.get("totalMoney"));
            promotionMoney = promotionMoney.add((BigDecimal) customPromotion.get("promotionMoney"));
            finalMoney = finalMoney.add((BigDecimal) customPromotion.get("finalMoney"));
        }
        obj.put("totalMoney", totalMoney);
        obj.put("promotionMoney", promotionMoney);
        obj.put("finalMoney", finalMoney);
        obj.put("promotions", customPromotions);
        return obj;
    }

    /** 找出每个产品所参加的优惠活动，得到key=promotionId,value=list<customItem>的map对象 */
    private Map<Long,ArrayList<HashMap<String,Object>>> getPromotionItems(List<HashMap<String, Object>> customItems) {
        Map<Long, ArrayList<HashMap<String, Object>>> promotionItems = new HashMap<>();
        for (HashMap<String, Object> customItem : customItems) {
            Boolean isGroupPromotion = (Boolean) customItem.get("isGroupPromotion");
            // 如果是组合产品
            if(isGroupPromotion){
                // 获取价格
                if (customItem.get("curPrice") == null) {
                    HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(((Integer) customItem.get("specialtyId")).longValue());
                    customItem.put("curPrice", groupitemPromotion.getSellPrice());
                }
                // 获取优惠活动id
                if (customItem.get("promotionId") == null) {
                    HyGroupitemPromotion groupitemPromotion = hyGroupitemPromotionFeignClient.getHyGroupitemPromotionById(((Integer) customItem.get("specialtyId")).longValue());
                    customItem.put("promotionId", groupitemPromotion.getPromotionId().intValue());
                }

                // 将该产品加入“promotionId”promotionItems中
                Long promotionId = ((Integer) customItem.get("promotionId")).longValue();
                if (!promotionItems.containsKey(promotionId)) {
                    promotionItems.put(promotionId, new ArrayList<>());
                }
                promotionItems.get(promotionId).add(customItem);
            }else{
                // 如果是普通产品
                // 获取价格和采购明细id
                if(customItem.get("curPrice") == null){
                    // 先去价格变化表里查
                    SpecialtyPrice specialtyPrice = specialtyPriceFeignClient.getSpecialtyPrice(((Integer) customItem.get("specialtySpecificationId")).longValue(), true);
                    if(specialtyPrice != null){
                        customItem.put("purchaseItemId", null);
                        customItem.put("curPrice", specialtyPrice.getPlatformPrice());
                    }else{
                        // 获取有效采购明细
                        PurchaseItem purchaseItem = getValidPurchaseItem(((Integer) customItem.get("specialtySpecificationId")).longValue());
                        customItem.put("purchaseItemId", purchaseItem.getId());
                        customItem.put("curPrice", purchaseItem.getSalePrice());
                    }
                }
                // 获取优惠活动id
                if(customItem.get("promotionId") == null){
                    // 获取有效优惠明细
                    HySingleitemPromotion singleitemPromotion = hySingleitemPromotionFeignClient.getValidSingleitemPromotion(((Integer) customItem.get("specialtySpecificationId")).longValue(), null);
                    // 如果没有优惠，则加入“0L”promotionItems中
                    if (singleitemPromotion == null) {
                        if (!promotionItems.containsKey(0L)) {
                            promotionItems.put(0L, new ArrayList<>());
                        }
                        promotionItems.get(0L).add(customItem);
                        // 如果有优惠，则加入“promotionId”promotionItems中
                    } else {
                        Long promotionId = singleitemPromotion.getPromotionId();
                        customItem.put("promotionId", promotionId);
                        if (!promotionItems.containsKey(promotionId)) {
                            promotionItems.put(promotionId, new ArrayList<>());
                        }
                        promotionItems.get(promotionId).add(customItem);
                    }
                }else{
                    // 将该产品加入“promotionId”promotionItems中
                    Long promotionId = ((Integer) customItem.get("promotionId")).longValue();
                    customItem.put("promotionId", promotionId);
                    if (!promotionItems.containsKey(promotionId)) {
                        promotionItems.put(promotionId, new ArrayList<>());
                    }
                    promotionItems.get(promotionId).add(customItem);
                }
            }
        }
        return promotionItems;
    }

    /** 获取某一规格有效采购批次*/
    private PurchaseItem getValidPurchaseItem(Long specialtySpecificationId) {
        List<PurchaseItem> purchaseItems = purchaseItemFeignClient.getPurchaseItemList(specialtySpecificationId, true, true);
        // 获取当前有效批次
        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return null;
        }
        PurchaseItem validPurchaseItem = purchaseItems.get(0);
        return validPurchaseItem;
    }
}
