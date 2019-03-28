package com.zmc.springcloud.feignclient.promotion;

import com.zmc.springcloud.entity.HyFullPresent;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-promotion")
public interface HyFullPresentFeignClient {
    @RequestMapping(value = "/hyfullpresent/list/{promotionId}")
    List<HyFullPresent> getHyFullPresentListByPromotionId(@PathVariable("promotionId") Long promotionId);
}
