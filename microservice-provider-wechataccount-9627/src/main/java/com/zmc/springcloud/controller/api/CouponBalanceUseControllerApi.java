package com.zmc.springcloud.controller.api;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.CouponBalanceUse;
import com.zmc.springcloud.service.CouponBalanceUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class CouponBalanceUseControllerApi {
    @Autowired
    private CouponBalanceUseService couponBalanceUseService;

    @RequestMapping("/coupon/balanceuse/save")
    public void save(CouponBalanceUse couponBalanceUse) throws Exception{
        couponBalanceUseService.save(couponBalanceUse);
    }

}
