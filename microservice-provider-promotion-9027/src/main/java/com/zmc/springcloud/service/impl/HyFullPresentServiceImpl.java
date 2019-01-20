package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyFullPresent;
import com.zmc.springcloud.mapper.HyFullPresentMapper;
import com.zmc.springcloud.service.HyFullPresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Service
public class HyFullPresentServiceImpl implements HyFullPresentService{
    @Autowired
    private HyFullPresentMapper hyFullPresentMapper;

    @Override
    public List<HyFullPresent> getHyFullPresentListByPromotionId(Long promotionId) throws Exception {
        return hyFullPresentMapper.getHyFullPresentListByPromotionId(promotionId);
    }
}
