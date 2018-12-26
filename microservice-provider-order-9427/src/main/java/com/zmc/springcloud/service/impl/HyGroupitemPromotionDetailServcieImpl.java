package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import com.zmc.springcloud.mapper.HyGroupitemPromotionDetailMapper;
import com.zmc.springcloud.service.HyGroupitemPromotionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class HyGroupitemPromotionDetailServcieImpl implements HyGroupitemPromotionDetailService{
    @Autowired
    private HyGroupitemPromotionDetailMapper hyGroupitemPromotionDetailMapper;

    @Override
    public List<HyGroupitemPromotionDetail> getHyGroupitemPromotionDetailList(Long groupItemPromotionId) throws Exception {
        return hyGroupitemPromotionDetailMapper.findListByGroupItemPromotionId(groupItemPromotionId);
    }
}
