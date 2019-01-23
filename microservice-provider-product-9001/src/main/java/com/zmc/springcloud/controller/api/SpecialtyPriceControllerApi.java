package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.SpecialtyPrice;
import com.zmc.springcloud.service.SpecialtyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@RestController
public class SpecialtyPriceControllerApi {
    @Autowired
    private SpecialtyPriceService specialtyPriceService;
    @RequestMapping(value = "/product/price")
    public SpecialtyPrice getSpecialtyPrice(@RequestParam("specialtySpecificationId") Long specialtySpecificationId, @RequestParam("isActive") Boolean isActive) throws Exception{
        return specialtyPriceService.find(specialtySpecificationId, isActive);
    }
}
