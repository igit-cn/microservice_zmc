package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**商城赠送电子券 hy_coupon_gift
 *
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Data
public class CouponGift {
    private Long id;
    private String couponCode;
    private Date issueTime;
    private Integer validityPeriod;
    private Float sum;
    private Integer state;
    private String receiver;
    private String receiverPhone;
    private String activationCode;
    private String bindPhone;
    private Date bindPhoneTime;
    private Long bindWechatAccountId;
    private Date bindWechatTime;
    private Date useTime;
    private Date expireTime;
    private Float couponCondition;
    private String orderCode;
    private Float useCouponAmount;
    private Integer isValid;
    /** 是否可叠加使用 */
    private Integer canOverlay;
    /** CouponMoney表的id 使能判断用户是否已经领取该电子券 */
    private Long couponMoneyId;
}
