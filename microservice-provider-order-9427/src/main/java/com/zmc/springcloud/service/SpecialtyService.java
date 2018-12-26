package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Specialty;


/**
 * Created by xyy on 2018/12/3.
 *
 * @author xyy
 */
public interface SpecialtyService {
    Specialty getSpecialtyId(Long specialtyId) throws Exception;
}
