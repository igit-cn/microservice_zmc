package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.SpecialtyAppraise;
import com.zmc.springcloud.service.SpecialtyAppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@RestController
public class SpecialtyAppraiseControllerApi {
    @Autowired
    private SpecialtyAppraiseService specialtyAppraiseService;

    /** 根据specialtyid获取isShow isValid的SpecialtyAppraise的列表*/
    @GetMapping("/specialty/appraise")
    public HashMap<String, Object> getSpecialtyAppraiseBySpecialtyId(@RequestParam("page")Integer page, @RequestParam("rows")Integer rows, @RequestParam("specialtyId")Long specialtyId) throws Exception{
        return specialtyAppraiseService.getSpecialtyAppraiseBySpecialtyId(page, rows, specialtyId);
    }
}
