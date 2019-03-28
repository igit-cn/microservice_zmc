package com.zmc.springcloud.feignclient.promotion;

import com.zmc.springcloud.entity.HyPromotionPic;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-promotion")
public interface HyPromotionPicFeignClient {
    @RequestMapping(value = "/promotion/pic/{promotionId}")
    List<HyPromotionPic> getHyPromotionPicListByPromotionId(@PathVariable("promotionId") Long promotionId);
}
