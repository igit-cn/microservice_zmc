package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.CouponBalanceUse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
@Mapper
public interface CouponBalanceUseMapper {
    @Insert("INSERT INTO hy_coupon_balance_use(coupon_id, order_code, wechat_id, use_amount, use_time, state, phone, coupon_code, type) VALUES(#{couponId}, #{orderCode}, #{wechatId}, #{useAmount}, #{useTime}, #{state}, #{phone}, #{couponCode}, #{type})")
    void insert(CouponBalanceUse couponBalanceUse);
}
