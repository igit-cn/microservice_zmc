package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyPromotion;
import com.zmc.springcloud.entity.HySingleitemPromotion;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.feignclient.product.SpecialtyFeignClient;
import com.zmc.springcloud.mapper.HySingleitemPromotionMapper;
import com.zmc.springcloud.service.HyPromotionService;
import com.zmc.springcloud.service.HySingleitemPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Service
public class HySingleitemPromotionServiceImpl implements HySingleitemPromotionService {
    @Autowired
    private SpecialtyFeignClient specialtyFeignClient;

    @Autowired
    private HySingleitemPromotionMapper hySingleitemPromotionMapper;

    @Autowired
    private HyPromotionService hyPromotionService;

    @Override
    public HySingleitemPromotion getValidSingleitemPromotion(Long specialtySpecificationId, Long promotionId) throws Exception {
        if (specialtySpecificationId == null || promotionId == null) {
            return null;
        }
        SpecialtySpecification specialtySpecification = specialtyFeignClient.getSpecialtySpecificationById(specialtySpecificationId);
        if (specialtySpecification == null) {
            return null;
        }
        HyPromotion promotion = hyPromotionService.getHyPromotionById(promotionId);
        if (promotion == null) {
            return null;
        }

        List<HySingleitemPromotion> singleitemPromotions = hySingleitemPromotionMapper.findList(specialtySpecificationId, promotionId);
        if (singleitemPromotions == null || singleitemPromotions.isEmpty()) {
            return null;
        }
        return singleitemPromotions.get(0);
    }

    @Override
    public void updatePromotion(HySingleitemPromotion singleitemPromotion) throws Exception {
        hySingleitemPromotionMapper.updatePromotion(singleitemPromotion);
    }
}
