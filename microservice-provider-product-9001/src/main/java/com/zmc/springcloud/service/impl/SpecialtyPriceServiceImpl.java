package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.SpecialtyPrice;
import com.zmc.springcloud.mapper.SpecialtyPriceMapper;
import com.zmc.springcloud.service.SpecialtyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/10.
 *
 * @author xyy
 */
@Service
public class SpecialtyPriceServiceImpl implements SpecialtyPriceService{
    @Autowired
    private SpecialtyPriceMapper specialtyPriceMapper;

    @Override
    public SpecialtyPrice find(Long specialtyId, boolean isActive) throws Exception{
        return specialtyPriceMapper.find(specialtyId, isActive);
    }

    @Override
    public void save(SpecialtyPrice newprice)throws Exception {
        specialtyPriceMapper.insert(newprice);
    }

    @Override
    public void updateSpecialtyPrice(Long id)throws Exception {
        specialtyPriceMapper.updateSpecialtyPrice(id);
    }
}
