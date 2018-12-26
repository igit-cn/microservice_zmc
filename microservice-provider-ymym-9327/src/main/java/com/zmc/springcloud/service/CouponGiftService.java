package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.CouponGift;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
public interface CouponGiftService {
    CouponGift find(long l) throws Exception;
    /** 将商城赠送电子券设置为使用状态*/
    void update(CouponGift coupon)throws Exception;
}
