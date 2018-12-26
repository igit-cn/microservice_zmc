package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_order_transaction  交易记录表
 *
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Data
public class OrderTransaction {
    private Long id;
    private String serialNum;
    private Long orderId;
    private BigDecimal wechatBalance;
    private BigDecimal orderCoupon;
    private BigDecimal payment;
    private Integer payType;
    private String payAccount;
    private Integer payFlow;
    private Date payTime;
}
