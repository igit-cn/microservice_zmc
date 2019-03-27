package com.zmc.springcloud.feignclient.product;

import com.zmc.springcloud.entity.ServicePromise;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by xyy on 2019/3/27.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface ServicePromiseFeignClient {
    /** 获取客户端 服务承诺*/
    @GetMapping("/service/promise")
    List<ServicePromise> getServicePromise();
}
