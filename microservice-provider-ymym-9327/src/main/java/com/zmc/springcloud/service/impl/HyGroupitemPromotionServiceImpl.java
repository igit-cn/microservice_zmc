package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.mapper.HyGroupitemPromotionMapper;
import com.zmc.springcloud.service.HyGroupitemPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Service
public class HyGroupitemPromotionServiceImpl implements HyGroupitemPromotionService{
    @Autowired
    private HyGroupitemPromotionMapper hyGroupitemPromotionMapper;

    @Override
    public HyGroupitemPromotion find(long id) throws Exception {
        return hyGroupitemPromotionMapper.find(id);
    }

    @Override
    public void updatePromotion(HyGroupitemPromotion groupitemPromotion) throws Exception{
        hyGroupitemPromotionMapper.updatePromotion(groupitemPromotion);
    }

}
