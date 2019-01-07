package com.zmc.springcloud.controller;

import com.zmc.springcloud.service.BusinessOrderService;
import com.zmc.springcloud.utils.CommonAttributes;
import com.zmc.springcloud.utils.Json;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.swing.text.EditorKit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@RestController()
public class OrderController {
    @Autowired
    private BusinessOrderService businessOrderService;

    @RequestMapping("/order/create")
    public Map<String, Object> createOrder(HashMap<String, Object> map) throws Exception{
        HashMap<String, Object> params = (HashMap<String, Object>) map.get("params");
        HashMap<String, Object> bodys = (HashMap<String, Object>) map.get("bodys");
        return businessOrderService.createOrder(params, bodys);
    }

    @RequestMapping("/order/updateafter")
    public void updateOrderAfterPay(String orderId)throws Exception{
        businessOrderService.updateOrderAfterPay(orderId);
    }


    /**
     * 渠道销售-订单审核-列表
     */
    @RequestMapping("/admin/business/order/page/view")
    public Json orderAuditList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Integer orderState, String orderCode, String orderPhone) {
        Json j = new Json();
        try {
            HashMap<String, Object> obj = businessOrderService.getOrderOList(page, rows, orderState, orderCode, orderPhone);
            j.setSuccess(true);
            j.setMsg("查询成功");
            j.setObj(obj);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 渠道销售-订单审核-订单详情*/
    @RequestMapping("/admin/business/order/detail/view")
    public Json orderDetail(Long orderid){
        Json j = new Json();
        try{
            HashMap<String, Object> obj = businessOrderService.getOrderDetail(orderid);
            j.setSuccess(true);
            j.setMsg("查询成功");
            j.setObj(obj);
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /** 渠道销售-订单审核-订单审核*/
    @RequestMapping("/admin/business/order/verify_agree")
    public Json verifyAgree(@RequestBody JSONObject body, HttpSession session){
        Json j = new Json();
        try{
            String usernameInSession = (String) session.getAttribute(CommonAttributes.Principal);
            j = businessOrderService.verifyAgree(body, usernameInSession);
        }catch (Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }
}
