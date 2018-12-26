package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Specialty;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
public interface SpecialtyService {
    /** 根据特产id获取特产*/
    Specialty find(Long specialtyId)throws Exception;
}
