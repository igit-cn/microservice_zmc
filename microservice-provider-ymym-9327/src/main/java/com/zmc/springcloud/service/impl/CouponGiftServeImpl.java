package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.CouponGift;
import com.zmc.springcloud.mapper.CouponGiftMapper;
import com.zmc.springcloud.service.CouponGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Service
public class CouponGiftServeImpl implements CouponGiftService{
    @Autowired
    private CouponGiftMapper counponMapper;
    @Override
    public CouponGift find(long id) throws Exception {
        return counponMapper.find(id);
    }

    @Override
    public void update(CouponGift coupon)throws Exception {
        counponMapper.update(coupon);
    }
}
