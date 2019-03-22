package com.zmc.springcloud.feignclient.product;

import com.zmc.springcloud.entity.BusinessBanner;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface BusinessBannerFeignClient {
    /** 根据BusinessBanner的状态获取列表*/
    @GetMapping("/businessbanner/list")
    List<BusinessBanner> getBusinessBannerList(@RequestParam("state")Boolean state);
}
