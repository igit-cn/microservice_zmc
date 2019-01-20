package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyFullSubstract;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
public interface HyFullSubstractService {
    /** 根据promotionId获取HyFullSubstract的list*/
    List<HyFullSubstract> getHyFullSubstractListByPromotionId(Long promotionId) throws Exception;
}
