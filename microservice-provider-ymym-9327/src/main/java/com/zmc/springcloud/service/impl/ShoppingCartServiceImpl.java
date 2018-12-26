package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.ShoppingCart;
import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.mapper.ShoppingCartMapper;
import com.zmc.springcloud.service.ShoppingCartService;
import com.zmc.springcloud.service.WechatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private WechatAccountService wechatAccountService;

    @Override
    public void addShoppingCart(ShoppingCart shoppingCart, Long wechatId) throws Exception {
        WechatAccount wechatAccount = wechatAccountService.findById(wechatId);
        if(wechatAccount == null){
            throw new Exception("用户不存在");
        }
        List<ShoppingCart> shoppingCartList = new LinkedList<>();
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
}
