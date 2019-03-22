package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.BusinessStore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface BusinessStoreFeignClient {
    @GetMapping("/businessstore/{id}")
    BusinessStore getBusinessStoreById(@PathVariable("id")Long id);
}
