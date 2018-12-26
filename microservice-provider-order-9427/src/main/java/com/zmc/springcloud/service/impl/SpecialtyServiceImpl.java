package com.zmc.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmc.springcloud.entity.CommonSequence.SequenceTypeEnum;
import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.entity.Specialty;
import com.zmc.springcloud.entity.SpecialtyImage;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.mapper.SpecialtyMapper;
import com.zmc.springcloud.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xyy on 2018/12/3.
 *
 * @author xyy
 */
@Service
public class SpecialtyServiceImpl implements SpecialtyService{
    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Override
    public Specialty getSpecialtyId(Long specialtyId) throws Exception {
        return specialtyMapper.findById(specialtyId);
    }
}
