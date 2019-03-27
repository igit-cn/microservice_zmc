package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyLabel;
import com.zmc.springcloud.mapper.HyLabelMapper;
import com.zmc.springcloud.service.HyLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Service
public class HyLabelServiceImpl implements HyLabelService {
    @Autowired
    private HyLabelMapper hyLabelMapper;

    @Override
    public List<HyLabel> getHyLableListBySpecialtyId(Long specialtyId) throws Exception {
        return hyLabelMapper.findListBySpecialtyId(specialtyId);
    }
}
