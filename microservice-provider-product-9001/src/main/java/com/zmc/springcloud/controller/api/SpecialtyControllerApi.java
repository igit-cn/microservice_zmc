package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/10.
 *
 * @author xyy
 */
@RestController
public class SpecialtyControllerApi {
    @Autowired
    private SpecialtyService specialtyService;

    @RequestMapping(value = "/product/specialty/{id}")
    public Specialty getSpecialtyById(@PathVariable("id") Long id){
        return specialtyService.getSpecialtyById(id);
    }

}
