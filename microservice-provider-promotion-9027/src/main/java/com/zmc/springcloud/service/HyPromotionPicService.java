package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.HyPromotionPic;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
public interface HyPromotionPicService {
    List<HyPromotionPic> getHyPromotionPicListByPromotionId(Long promotionId) throws Exception;
}
