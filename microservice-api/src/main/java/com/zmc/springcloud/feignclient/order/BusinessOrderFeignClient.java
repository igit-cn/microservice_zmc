package com.zmc.springcloud.feignclient.order;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-order")
public interface BusinessOrderFeignClient {
    @RequestMapping(value = "/order/create",method = RequestMethod.GET)
    Map<String, Object> createOrder(@RequestParam("map") HashMap<String, Object> map);

    @RequestMapping("/order/updateafter")
    void updateOrderAfterPay(@RequestParam String orderId);
}
