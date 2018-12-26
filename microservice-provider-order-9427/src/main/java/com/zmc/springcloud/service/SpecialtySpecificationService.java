package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.SpecialtySpecification;

import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
public interface SpecialtySpecificationService {
    SpecialtySpecification getSpecialtySpecificationById(Long specialtySpecificationId) throws Exception;
}
