package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * hy_coupon_balance_use  余额电子券使用记录
 *
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
@Data
public class CouponBalanceUse {
    private Long id;
    /** 1 线路赠送  2 销售奖励  3 商城销售  4 大客户购买  表的id */
    private Long couponId;
    private String orderCode;
    private Long wechatId;
    private Float useAmount;
    /** 绑定时间*/
    private Date useTime;
    private Integer state;
    /** 绑定的手机号*/
    private String phone;
    private String couponCode;
    /** 1 线路赠送  2 销售奖励  3 商城销售  4 大客户购买,5积分兑换,6首单奖励*/
    private Integer type;
}
