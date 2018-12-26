package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyGroupitemPromotion;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface HyGroupitemPromotionService {
    HyGroupitemPromotion find(long specialtyId) throws Exception;
    /** 修改优惠活动数量*/
    void updatePromotion(HyGroupitemPromotion groupitemPromotion)throws Exception;
}
