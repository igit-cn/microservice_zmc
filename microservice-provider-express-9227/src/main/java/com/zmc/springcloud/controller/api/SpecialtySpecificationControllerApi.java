package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.service.SpecialtySpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class SpecialtySpecificationControllerApi {
    @Autowired
    private SpecialtySpecificationService specialtySpecificationService;

    @RequestMapping(value = "/product/specification/{id}")
    public SpecialtySpecification getSpecialtySpecificationById(@PathVariable("id") Long id) throws Exception{
        return specialtySpecificationService.findById(id);
    }

    @RequestMapping(value = "/product/specification/parent/list/{id}")
    public List<Map<String, Object>> getParentSpecificationList(@PathVariable("id") Long id) throws Exception{
        return specialtySpecificationService.getParentSpecificationList(id);
    }

    @RequestMapping(value = "/product/specification/all/{id}")
    public List<SpecialtySpecification> getAllSpecification(@PathVariable("id") Long id) throws Exception{
        return specialtySpecificationService.getAllSpecification(id);
    }

    @RequestMapping(value = "/product/specification/add")
    public void batchInsert(@RequestBody List<SpecialtySpecification> specialtySpecificationList) throws Exception{
        specialtySpecificationService.batchInsert(specialtySpecificationList);
    }

    @RequestMapping(value = "/product/specification/update")
    public void update(@RequestBody SpecialtySpecification spe) throws Exception{
        specialtySpecificationService.update(spe);
    }

    @RequestMapping(value = "/product/specification/baseinbound")
    public boolean isBaseInboundEnough(@RequestBody List<Map<String, Object>> orderItems) throws Exception{
        return specialtySpecificationService.isBaseInboundEnough(orderItems);
    }

    @RequestMapping(value = "/product/specification/baseinbound/update")
    public void updateBaseInboundAndHasSold(@RequestBody BusinessOrderItem businessOrderItem, @RequestParam Boolean isSale) throws Exception{
        specialtySpecificationService.updateBaseInboundAndHasSold(businessOrderItem, isSale);
    }
}
