package com.zmc.springcloud.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface BusinessOrderService {
    /** 创建订单 注意返回orderId和orderCode给前端*/
    Map<String, Object> createOrder(HashMap<String, Object> params, HashMap<String, Object> bodys) throws Exception;
    /**支付成功，处理回调,修改订单状态*/
    void updateOrderAfterPay(String orderId)throws Exception;
}
