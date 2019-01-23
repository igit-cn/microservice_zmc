package com.zmc.springcloud.feignclient.express;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.SpecialtySpecification;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-express")
public interface SpecialtySpecificationFeignClient {
    @RequestMapping(value = "/product/specification/{id}")
    SpecialtySpecification getSpecialtySpecificationById(@PathVariable("id") Long id);

    @RequestMapping(value = "/product/specification/parent/list/{id}")
    List<Map<String, Object>> getParentSpecificationList(@PathVariable("id") Long id);

    @RequestMapping(value = "/product/specification/all/{id}")
    List<SpecialtySpecification> getAllSpecification(@PathVariable("id") Long id);

    @RequestMapping(value = "/product/specification/add")
    void batchInsert(@RequestBody List<SpecialtySpecification> specialtySpecificationList);

    @RequestMapping(value = "/product/specification/update")
    void update(@RequestBody SpecialtySpecification spe);

    @RequestMapping(value = "/product/specification/baseinbound")
    boolean isBaseInboundEnough(@RequestBody List<Map<String, Object>> orderItems);

    @RequestMapping(value = "/product/specification/baseinbound/update", method = RequestMethod.POST)
    void updateBaseInboundAndHasSold(@RequestParam("businessOrderItem") BusinessOrderItem businessOrderItem, @RequestParam("isSale") Boolean isSale);
}
