package com.zmc.springcloud.controller.web;

import com.zmc.springcloud.entity.ShoppingCart;
import com.zmc.springcloud.service.ShoppingCartService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /** 获取购物车列表*/
    @RequestMapping(value = "/shopping_cart/get_items")
    public Json getList(Long wechat_id){
        Json j = new Json();
        try{
            j = shoppingCartService.getShoppingCartList(wechat_id);
        }catch (Exception e){
            e.printStackTrace();
            j.setMsg("操作失败");
            j.setSuccess(false);
        }
        return j;
    }

    /** 添加到购物车  实体中是wechatId而不是wechat_id 增加一个参数接收*/
    @RequestMapping(value = "/shopping_cart/add_items")
    public Json add(ShoppingCart shoppingCart, Long wechat_id){
        Json j = new Json();
        try{
            shoppingCartService.addShoppingCart(shoppingCart, wechat_id);
            j.setMsg("操作成功");
            j.setSuccess(true);
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 删除购物车的条目*/
    @RequestMapping(value = "/shopping_cart/delete_items")
    public Json delete(Long id){
        Json j = new Json();
        try{
            shoppingCartService.delete(id);
            j.setSuccess(true);
            j.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }

    /** 编辑购物车*/
    @RequestMapping(value = "/shopping_cart/edit_items")
    public Json edit(ShoppingCart shoppingCart){
       Json j = new Json();
       try{
            shoppingCartService.updateQuantity(shoppingCart);
       }catch(Exception e){
           e.printStackTrace();
           j.setSuccess(false);
           j.setMsg("操作失败");
       }
       return j;
    }

    /** 计算选中的总价*/
    @RequestMapping(value = "/shopping_cart/total_price")
    public Json totalPrice(List<HashMap<String, Object>> params, List<HashMap<String, Object>> bodys, HttpSession session){
        Json j = new Json();
        try{
            Long wechat_id = (Long) session.getAttribute("wechat_id");
            HashMap<String, Object> map = shoppingCartService.totalPrice(params, bodys, wechat_id);
            j.setSuccess(true);
            j.setMsg("操作成功");
            j.setObj(map);
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("操作失败");
        }
        return j;
    }
}
