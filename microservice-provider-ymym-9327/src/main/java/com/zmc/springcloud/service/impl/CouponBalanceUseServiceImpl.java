package com.zmc.springcloud.service.impl;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.CouponBalanceUse;
import com.zmc.springcloud.mapper.CouponBalanceUseMapper;
import com.zmc.springcloud.service.CouponBalanceUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
@Service
public class CouponBalanceUseServiceImpl implements CouponBalanceUseService{
    @Autowired
    private CouponBalanceUseMapper couponBalanceUseMapper;
    @Override
    public void save(CouponBalanceUse couponBalanceUse) throws Exception {
        couponBalanceUseMapper.insert(couponBalanceUse);
    }
}
