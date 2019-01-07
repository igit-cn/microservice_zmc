package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.CouponGift;
import com.zmc.springcloud.service.CouponGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class CouponGiftController {
    @Autowired
    private CouponGiftService couponGiftService;

    @RequestMapping("/coupon/gift/{id}")
    public CouponGift getCouponGiftById(@PathVariable("id")  Long id) throws Exception{
        return  couponGiftService.findCouponGiftById(id);
    }

    /** 将商城赠送电子券设置为使用状态*/
    @RequestMapping("/coupon/gift/update")
    public void updateUseState(CouponGift coupon) throws Exception{
        couponGiftService.updateUseState(coupon);
    }
}
