package com.zmc.springcloud.service.impl;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.HyPromotion;
import com.zmc.springcloud.mapper.HyPromotionMapper;
import com.zmc.springcloud.service.HyPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Service
public class HyPromotionServiceImpl implements HyPromotionService{
    @Autowired
    private HyPromotionMapper hyPromotionMapper;

    @Override
    public HyPromotion find(Long promotionId) throws Exception {
        return hyPromotionMapper.find(promotionId);
    }
}
