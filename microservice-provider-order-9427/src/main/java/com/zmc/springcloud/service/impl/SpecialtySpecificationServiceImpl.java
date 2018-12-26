package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.mapper.SpecialtySpecificationMapper;
import com.zmc.springcloud.service.SpecialtySpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Service
public class SpecialtySpecificationServiceImpl implements SpecialtySpecificationService {
    @Autowired
    private SpecialtySpecificationMapper specialtySpecificationMapper;

    @Override
    public SpecialtySpecification getSpecialtySpecificationById(Long specialtySpecificationId) throws Exception {
        return specialtySpecificationMapper.findById(specialtySpecificationId);
    }
}
