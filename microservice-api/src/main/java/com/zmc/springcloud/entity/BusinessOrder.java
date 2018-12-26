package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_business_order 订单表
 * Created by xyy on 2018/12/5.
 * @author xyy
 */
@Data
public class BusinessOrder {
    private Long id;
    private String orderCode;
    private String orderPhone;
    private Long orderWechatId;
    private Long weBusinessId;
    /**
     * 总金额
     */
    private BigDecimal totalMoney;
    /**
     * 优惠金额
     */
    private BigDecimal promotionAmount;
    private BigDecimal shipFee;
    /**
     * 应付金额 =  总金额 － 优惠金额 ＋ 物流费
     */
    private BigDecimal shouldpayMoney;
    /**
     * 使用一次电子券金额
     */
    private BigDecimal couponMoney;
    /**
     * 使用余额金额
     */
    private BigDecimal balanceMoney;
    /**
     * 使用微信支付金额
     */
    private BigDecimal payMoney;
    private BigDecimal refoundMoney;
    private Integer orderState;
    private Date orderTime;
    private Date reviewTime;
    private Date payTime;
    private Date deliveryTime;
    private Date receiveTime;
    private Date orderCancelTime;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverRemark;
    private Integer receiveType;
    private String invoiceTitle;
    private String taxpayerCode;
    /**
     * 是否统计分成
     */
    private Boolean isBalanced;
    private Long promotionId;
    private String couponId;

    private Long shipId;

    /**
     * 是否由供应商发货
     */
    private Boolean isDivided;

    /**
     * 父订单id
     */
    private Long parentOrderId;

    /**
     * 是否是原始凭证
     */
    private Boolean isShow;

    private Boolean isAppraised;

    /**
     * 审核人
     */
    private String reviewer;

    /**
     * 是否结算
     */
    private Boolean isBalance;

    /**
     * 完成结算时间
     */
    private Date completeTime;

    /**
     * 是否有效
     */
    private Boolean isValid;
}
