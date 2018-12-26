package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.ShoppingCart;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
public interface ShoppingCartService {
    /** 添加到购物车*/
    void addShoppingCart(ShoppingCart shoppingCart, Long wechat_id) throws Exception;
}
