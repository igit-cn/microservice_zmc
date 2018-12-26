package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;

import java.util.List;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface HyGroupitemPromotionDetailService {
    List<HyGroupitemPromotionDetail> getHyGroupitemPromotionDetailList(Long groupItemPromotionId) throws Exception;
}
