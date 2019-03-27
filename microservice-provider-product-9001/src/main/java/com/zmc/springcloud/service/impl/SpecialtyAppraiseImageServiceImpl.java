package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.SpecialtyAppraiseImage;
import com.zmc.springcloud.mapper.SpecialtyAppraiseImageMapper;
import com.zmc.springcloud.service.SpecialtyAppraiseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Service
public class SpecialtyAppraiseImageServiceImpl implements SpecialtyAppraiseImageService{
    @Autowired
    private SpecialtyAppraiseImageMapper specialtyAppraiseImageMapper;

    @Override
    public List<SpecialtyAppraiseImage> getAppraiseImage(Long specialtyAppraiseId) throws Exception {
        return specialtyAppraiseImageMapper.findListBySpecialtyAppraiseId(specialtyAppraiseId);
    }
}
