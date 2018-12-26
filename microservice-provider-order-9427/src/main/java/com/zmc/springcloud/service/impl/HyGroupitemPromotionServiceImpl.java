package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.mapper.HyGroupitemPromotionMapper;
import com.zmc.springcloud.service.HyGroupitemPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class HyGroupitemPromotionServiceImpl implements HyGroupitemPromotionService {
    @Autowired
    private HyGroupitemPromotionMapper hyGroupitemPromotionMapper;

    @Override
    public HyGroupitemPromotion getHyGroupitemPromotionById(Long specialtyId) throws Exception {
        return hyGroupitemPromotionMapper.findById(specialtyId);
    }
}
