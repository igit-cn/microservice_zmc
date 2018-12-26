package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单 hy_purchase
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Data
public class Purchase {
    private Long id;
    private BigDecimal advanceAmount;
    private Date advanceTime;
    private String creator;
    private String purchaseCode;
    private Date purchaseTime;
    private Integer purchaseType;
    private Date receiveTime;
    private Date reviewTime;
    private Integer status;
    private Long providerId;
    private Date setDivideProportionTime;
    private Date setShipInfoTime;
    private Date balancePaySubmitTime;
    private Date balancePayTime;
    private String processInstanceId;
    private Boolean isValid;
    private BigDecimal totalMoney;
}
