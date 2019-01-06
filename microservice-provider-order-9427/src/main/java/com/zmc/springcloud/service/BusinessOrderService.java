package com.zmc.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.zmc.springcloud.utils.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface BusinessOrderService {
    /** 渠道销售-订单审核-列表*/
    HashMap<String,Object> getOrderOList(Integer page, Integer rows, Integer orderState, String orderCode, String orderPhone) throws  Exception;
    /** 订单详情*/
    HashMap<String,Object> getOrderDetail(Long orderid) throws Exception;
    /** 渠道销售-订单审核-订单审核*/
    Json verifyAgree(JSONObject body, String usernameInSession) throws Exception;


    /** 创建订单 注意返回orderId和orderCode给前端*/
    Map<String, Object> createOrder(HashMap<String, Object> params, HashMap<String, Object> bodys) throws Exception;
    /**支付成功，处理回调,修改订单状态*/
    void updateOrderAfterPay(String orderId)throws Exception;
}
