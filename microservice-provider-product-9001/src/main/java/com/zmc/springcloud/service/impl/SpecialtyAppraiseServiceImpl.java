package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.mapper.SpecialtyAppraiseMapper;
import com.zmc.springcloud.service.SpecialtyAppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Service
public class SpecialtyAppraiseServiceImpl implements SpecialtyAppraiseService {
    @Autowired
    private SpecialtyAppraiseMapper specialtyAppraiseMapper;
    @Override
    public Long count(Long specialtyId)throws Exception {
        return specialtyAppraiseMapper.count(specialtyId);
    }
}
