package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.service.BusinessOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyy on 2019/1/10.
 *
 * @author xyy
 */
@RestController
public class OrderControllerApi {
    @Autowired
    private BusinessOrderService businessOrderService;

    @RequestMapping(value = "/order/create", method = RequestMethod.GET)
    public Map<String, Object> createOrder(@RequestParam("map") HashMap<String, Object> map) throws Exception {
        HashMap<String, Object> params = (HashMap<String, Object>) map.get("params");
        HashMap<String, Object> bodys = (HashMap<String, Object>) map.get("bodys");
        return businessOrderService.createOrder(params, bodys);
    }

    @RequestMapping(value = "/order/updateafter", method = RequestMethod.GET)
    public void updateOrderAfterPay(@RequestParam("orderId") String orderId) throws Exception {
        businessOrderService.updateOrderAfterPay(orderId);
    }
}
