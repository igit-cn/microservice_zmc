package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyFullDiscount;
import com.zmc.springcloud.mapper.HyFullDiscountMapper;
import com.zmc.springcloud.service.HyFullDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Service
public class HyFullDiscountServiceImpl implements HyFullDiscountService{
    @Autowired
    private HyFullDiscountMapper hyFullDiscountMapper;

    @Override
    public List<HyFullDiscount> getHyFullDiscountListByPromotionId(Long promotionId) throws Exception {
        return hyFullDiscountMapper.getHyFullDiscountListByPromotionId(promotionId);
    }
}
