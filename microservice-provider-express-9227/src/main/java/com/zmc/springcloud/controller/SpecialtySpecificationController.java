package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.service.SpecialtySpecificationService;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class SpecialtySpecificationController {
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
    public void batchInsert(List<SpecialtySpecification> specialtySpecificationList) throws Exception{
        specialtySpecificationService.batchInsert(specialtySpecificationList);
    }

    @RequestMapping(value = "/product/specification/update")
    public void update(SpecialtySpecification spe) throws Exception{
        specialtySpecificationService.update(spe);
    }

    @RequestMapping(value = "/product/specification/baseinbound")
    public boolean isBaseInboundEnough(List<Map<String, Object>> orderItems) throws Exception{
        return specialtySpecificationService.isBaseInboundEnough(orderItems);
    }

    @RequestMapping(value = "/product/specification/baseinbound/update")
    public void updateBaseInboundAndHasSold(BusinessOrderItem businessOrderItem, Boolean isSale) throws Exception{
        specialtySpecificationService.updateBaseInboundAndHasSold(businessOrderItem, isSale);
    }
}
