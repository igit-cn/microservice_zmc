package com.zmc.springcloud.feignclient.product;

import com.zmc.springcloud.entity.Specialty;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface SpecialtyFeingClient {
    @RequestMapping(value = "/product/specialty/{id}")
    Specialty getSpecialtyById(@PathVariable("id") Long id);
}
