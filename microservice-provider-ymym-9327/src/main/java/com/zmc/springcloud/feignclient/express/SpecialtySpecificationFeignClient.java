package com.zmc.springcloud.feignclient.express;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.SpecialtySpecification;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-express")
public interface SpecialtySpecificationFeignClient {
    @RequestMapping(value = "/product/specification/{id}")
    SpecialtySpecification getSpecialtySpecificationById(@PathVariable("id") Long id);

    @RequestMapping(value = "/product/specification/baseinbound/update")
    void updateBaseInboundAndHasSold(@RequestBody BusinessOrderItem businessOrderItem, @RequestParam Boolean isSale);
}
