package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_business_order_refund
 *
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
@Data
public class BusinessOrderRefund {
    private Long id;
    private Integer deliverType;
    private BigDecimal erefundAmount;
    private Date inboundTime;
    private Boolean isDelivered;
    private BigDecimal qrefundAmount;
    private BigDecimal rrefundAmount;
    private String receivePhone;
    private String receiverName;
    private Date refundAcceptTime;
    private BigDecimal refundAmount;
    private Date refundApplyTime;
    private String refundReason;
    private String refundShipCode;
    private BigDecimal refundShipFee;
    private String refundShiper;
    private BigDecimal refundTotalamount;
    /**
     * 1:平台      2:供应商
     **/
    private Integer responsibleParty;
    private Date returnCompleteTime;
    private Date returnMoneyTime;
    private Date shipTime;
    /**
     * 1待售后人员确认   2待消费者退货    3待库管入库    4待财务退款   5已完成
     */
    private Integer state;
    private Long businessOrderId;
    private Long wechatId;
}
