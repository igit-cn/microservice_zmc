package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyPromotion;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface HyPromotionService {
    HyPromotion find(Long promotionId) throws Exception;
}
