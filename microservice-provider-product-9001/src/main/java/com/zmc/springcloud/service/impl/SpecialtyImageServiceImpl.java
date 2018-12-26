package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.SpecialtyImage;
import com.zmc.springcloud.mapper.SpecialtyImageMapper;
import com.zmc.springcloud.service.SpecialtyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Service
public class SpecialtyImageServiceImpl implements SpecialtyImageService {
    @Autowired
    private SpecialtyImageMapper specialtyImageMapper;

    @Override
    public void batchInsert(List<SpecialtyImage> specialtyImageList) throws Exception{
        specialtyImageMapper.batchInsert(specialtyImageList);
    }

    @Override
    public void insert(SpecialtyImage s) throws Exception {
        specialtyImageMapper.insert(s);
    }

    @Override
    public List<SpecialtyImage> getSpecialtyImageList(Long id)throws Exception {
        return specialtyImageMapper.getListSpecailtyBySpecialtyId(id);
    }
}
