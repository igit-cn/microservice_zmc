package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.Store;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface StoreFeignClient {
    @GetMapping("/store/{id}")
    Store getStoreById(@PathVariable("id")Long id);
}
