package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.service.SpecialtyService;
import com.zmc.springcloud.utils.ArrayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
