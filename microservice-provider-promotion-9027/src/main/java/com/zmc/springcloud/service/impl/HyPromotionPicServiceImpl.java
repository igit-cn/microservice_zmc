package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyPromotionPic;
import com.zmc.springcloud.mapper.HyPromotionPicMapper;
import com.zmc.springcloud.service.HyPromotionPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@Service
public class HyPromotionPicServiceImpl implements HyPromotionPicService{
    @Autowired
    private HyPromotionPicMapper hyPromotionPicMapper;

    @Override
    public List<HyPromotionPic> getHyPromotionPicListByPromotionId(Long promotionId) throws Exception {
        return hyPromotionPicMapper.getHyPromotionListByPromotionId(promotionId);
    }
}
