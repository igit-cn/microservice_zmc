package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.mapper.SpecialtySpecificationMapper;
import com.zmc.springcloud.service.SpecialtySpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void batchInsert(List<SpecialtySpecification> specialtySpecificationList)throws Exception {
        specialtySpecificationMapper.batchInsert(specialtySpecificationList);
    }


    @Override
    public List<Map<String, Object>> getParentSpecificationList(Long specialtyid) throws Exception{
        List<SpecialtySpecification> list = specialtySpecificationMapper.getParentSpecificationListByspecialtyid(specialtyid);

        List<Map<String, Object>> res = new ArrayList<>();
        for (SpecialtySpecification specification : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", specification.getId());
            map.put("specification", specification.getSpecification());
            res.add(map);
        }

        return res;
    }

    @Override
    public List<SpecialtySpecification> getAllSpecification(Long id) throws Exception{
        return specialtySpecificationMapper.findAllSpecificationBySpecialtyId(id);
    }

    @Override
    public SpecialtySpecification findById(Long specificationsId) throws Exception{
        return specialtySpecificationMapper.findById(specificationsId);
    }

    @Override
    public void update(SpecialtySpecification specialtySpecification) throws Exception{
        specialtySpecificationMapper.update(specialtySpecification);
    }
}
