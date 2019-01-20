package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyFullPresent;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
public interface HyFullPresentService {
    /** 根据promotionId获取HyFullPresent的list*/
    List<HyFullPresent> getHyFullPresentListByPromotionId(Long promotionId) throws Exception;
}
