package com.zmc.springcloud.controller.api;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.BusinessBanner;
import com.zmc.springcloud.service.BusinessBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@RestController
public class BusinessBannerControllerApi {
    @Autowired
    private BusinessBannerService businessBannerService;

    @GetMapping("/businessbanner/list")
    public List<BusinessBanner> getBusinessBannerList(@RequestParam("state")Boolean state){
        return businessBannerService.getBusinessBannerList(state);
    }
}
