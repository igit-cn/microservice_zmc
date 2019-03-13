package com.zmc.springcloud.feignclient.promotion;

import com.zmc.springcloud.entity.HyPromotion;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-promotion")
public interface HyPromotionFeignClient {
    @RequestMapping("/promotion/{id}")
    HyPromotion getHyPromotionById(@PathVariable("id") Long id);
}
