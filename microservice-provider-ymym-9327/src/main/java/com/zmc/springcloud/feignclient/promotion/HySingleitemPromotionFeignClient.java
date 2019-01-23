package com.zmc.springcloud.feignclient.promotion;

import com.zmc.springcloud.entity.HySingleitemPromotion;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-promotion")
public interface HySingleitemPromotionFeignClient {
    @RequestMapping("/promotion/singleitem/")
    HySingleitemPromotion getValidSingleitemPromotion(@RequestParam Long specialtySpecificationId, @RequestParam Long promotionId);

    @RequestMapping("/promotion/singleitem/update")
    void updateSingleItemPromotion(@RequestBody HySingleitemPromotion singleitemPromotion);
}
