package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyFullDiscount;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
public interface HyFullDiscountService {
    /** 根据promotionId获取HyFullDiscount的list*/
    List<HyFullDiscount> getHyFullDiscountListByPromotionId(Long promotionId) throws Exception;
}
