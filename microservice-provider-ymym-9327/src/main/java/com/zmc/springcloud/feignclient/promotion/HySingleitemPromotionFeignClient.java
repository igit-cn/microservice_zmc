package com.zmc.springcloud.feignclient.promotion;

import com.zmc.springcloud.entity.HySingleitemPromotion;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-promotion")
public interface HySingleitemPromotionFeignClient {
    @RequestMapping("/promotion/singleitem/")
    HySingleitemPromotion getValidSingleitemPromotion(Long specialtySpecificationId, Long promotionId);
}
