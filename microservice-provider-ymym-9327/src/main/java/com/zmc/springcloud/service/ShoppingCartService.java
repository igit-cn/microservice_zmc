package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.ShoppingCart;
import com.zmc.springcloud.utils.Json;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
public interface ShoppingCartService {
    /** 添加到购物车*/
    void addShoppingCart(ShoppingCart shoppingCart, Long wechat_id) throws Exception;
    /** 获取购物车列表*/
    Json getShoppingCartList(Long wechat_id);
    /** 删除购物车条目*/
    void delete(Long id);
    /** 编辑购物车 只是编辑数据*/
    void updateQuantity(ShoppingCart shoppingCart);
    /** 计算总价格*/
    HashMap<String,Object> totalPrice(List<HashMap<String, Object>> params, List<HashMap<String, Object>> bodys, Long wechat_id);
}
