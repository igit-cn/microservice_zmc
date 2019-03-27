package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HySpecialtyLabel;
import com.zmc.springcloud.mapper.HySpecialtyLabelMapper;
import com.zmc.springcloud.service.HySpecialtyLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Service
public class HySpecialtyLabelServiceImpl implements HySpecialtyLabelService {
    @Autowired
    private HySpecialtyLabelMapper hySpecialtyLabelMapper;

    @Override
    public boolean isMarked(Long labelId, Long specialtyId, boolean isMarked) throws Exception {
        List<HySpecialtyLabel> hySpecialtyLabels = hySpecialtyLabelMapper.findHySpecialtyLabelList(labelId, specialtyId, isMarked);
        if(hySpecialtyLabels == null || hySpecialtyLabels.isEmpty()){
            return false;
        }
        return true;
    }
}
