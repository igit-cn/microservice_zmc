package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.entity.HyVinbound;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.mapper.HyVinboundMapper;
import com.zmc.springcloud.service.HyVinboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/7.
 *
 * @author xyy
 */
@Service
public class HyVinboundServiceImpl implements HyVinboundService{
    @Autowired
    private HyVinboundMapper hyVinboundMapper;

    @Override
    public List<HyVinbound> getHyVinboundListBySpecification(SpecialtySpecification fuSpecification) throws Exception {
        return hyVinboundMapper.findListBySpecificationId(fuSpecification.getId());
    }

    @Override
    public void updateHyVinboundNum(Long id, Integer saleNumber, Integer vinboundNumber) throws Exception {
        hyVinboundMapper.updateHyVinboundNum(id, saleNumber, vinboundNumber);
    }
}
