package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HySingleitemPromotion;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface HySingleitemPromotionService {
    /** 获取有效普通优惠明细 */
    HySingleitemPromotion getValidSingleitemPromotion(Long specialtySpecificationId, Long promotionId) throws Exception;
    /** 更新优惠数量*/
    void updatePromotion(HySingleitemPromotion singleitemPromotion)throws Exception;
}
