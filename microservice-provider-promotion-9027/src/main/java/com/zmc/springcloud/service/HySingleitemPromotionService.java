package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HySingleitemPromotion;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface HySingleitemPromotionService {
    /** 获取有效普通优惠明细 */
    // 将虹宇中的两个getValidSingleitemPromotion()方法合并, 使用prpmotionId是否为null作为判断
    HySingleitemPromotion getValidSingleitemPromotion(Long specialtySpecificationId, Long promotionId) throws Exception;
    /** 更新优惠数量*/
    void updatePromotion(HySingleitemPromotion singleitemPromotion)throws Exception;
}
