package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * hy_business_order_outbound
 *
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
@Data
public class BusinessOrderOutbound {
    private Long id;
    private Long orderItemId;
    private Long inboundId;
    private String depotCode;
    private Integer outboundQuantity;
    private Date outboundTime;
    private String operator;
    private Integer returnQuantity;
}
