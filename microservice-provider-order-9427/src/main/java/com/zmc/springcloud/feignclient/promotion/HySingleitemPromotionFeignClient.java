package com.zmc.springcloud.feignclient.promotion;

import com.zmc.springcloud.entity.HySingleitemPromotion;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xyy on 2019/1/5.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-promotion")
public interface HySingleitemPromotionFeignClient {
    @RequestMapping(value = "/promotion/singleitem/", method = RequestMethod.GET)
    HySingleitemPromotion getValidSingleitemPromotion(@RequestParam("specialtySpecificationId") Long specialtySpecificationId, @RequestParam("promotionId") Long promotionId);

    @RequestMapping("/promotion/singleitem/update")
    void updateSingleItemPromotion(HySingleitemPromotion singleitemPromotion);
}

