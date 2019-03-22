package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyPromotion;
import com.zmc.springcloud.entity.HySingleitemPromotion;
import com.zmc.springcloud.entity.SpecialtySpecification;
import com.zmc.springcloud.feignclient.product.SpecialtySpecificationFeignClient;
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
    private SpecialtySpecificationFeignClient specialtySpecificationFeignClient;

    @Autowired
    private HySingleitemPromotionMapper hySingleitemPromotionMapper;

    @Autowired
    private HyPromotionService hyPromotionService;

    @Override
    public HySingleitemPromotion getValidSingleitemPromotion(Long specialtySpecificationId, Long promotionId) throws Exception {
        // 将虹宇中的两个getValidSingleitemPromotion()方法合并, 使用prpmotionId是否为null作为判断
        if (specialtySpecificationId == null) {
            return null;
        }
        SpecialtySpecification specialtySpecification = specialtySpecificationFeignClient.getSpecialtySpecificationById(specialtySpecificationId);
        if (specialtySpecification == null) {
            return null;
        }
        if (null == promotionId) {
            List<HySingleitemPromotion> singleitemPromotions = hySingleitemPromotionMapper.findList(specialtySpecificationId, null);
            if (singleitemPromotions == null || singleitemPromotions.isEmpty()) {
                return null;
            }
            for (HySingleitemPromotion singleitemPromotion : singleitemPromotions) {
                HyPromotion promotion = hyPromotionService.getHyPromotionById(singleitemPromotion.getPromotionId());
                if (promotion.getStatus() == HyPromotion.PromotionStatus.进行中) {
                    return singleitemPromotion;
                }
            }
            return null;
        } else {
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
    }

    @Override
    public void updatePromotion(HySingleitemPromotion singleitemPromotion) throws Exception {
        hySingleitemPromotionMapper.updatePromotion(singleitemPromotion);
    }
}
