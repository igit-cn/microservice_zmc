package com.zmc.springcloud.feignclient.order;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-order")
public interface BusinessOrderFeignClient {
    @RequestMapping("/order/create")
    Map<String, Object> createOrder(HashMap<String, Object> params, HashMap<String, Object> bodys);

    @RequestMapping("/order/")
    void updateOrderAfterPay(String orderId);
}
