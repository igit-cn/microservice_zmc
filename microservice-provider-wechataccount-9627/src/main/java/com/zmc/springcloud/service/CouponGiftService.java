package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.CouponGift;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface CouponGiftService {
    CouponGift findCouponGiftById(Long id) throws Exception;
    /** 将商城赠送电子券设置为使用状态*/
    void updateUseState(CouponGift coupon)throws Exception;
}
