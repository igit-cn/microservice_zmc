package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyPromotion;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface HyPromotionService {
    HyPromotion getHyPromotionById(Long promotionId) throws Exception;
}
