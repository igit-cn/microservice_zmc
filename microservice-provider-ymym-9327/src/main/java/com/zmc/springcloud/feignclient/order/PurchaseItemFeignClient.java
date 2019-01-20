package com.zmc.springcloud.feignclient.order;

import com.zmc.springcloud.entity.PurchaseItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-order")
public interface PurchaseItemFeignClient {
    @RequestMapping("/order/purchaseitem/list")
    List<PurchaseItem> getPurchaseItemList(@RequestParam("specificationId") Long specificationId, @RequestParam("isValid") Boolean isValid, @RequestParam("state") Boolean state);
}
