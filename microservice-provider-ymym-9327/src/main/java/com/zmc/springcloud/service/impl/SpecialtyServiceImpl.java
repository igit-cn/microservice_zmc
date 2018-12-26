package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.mapper.SpecialtyMapper;
import com.zmc.springcloud.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
@Service
public class SpecialtyServiceImpl implements SpecialtyService{
    @Autowired
    private SpecialtyMapper specialtyMapper;
    @Override
    public Specialty find(Long specialtyId)throws Exception {
        return specialtyMapper.findById(specialtyId);
    }
}
