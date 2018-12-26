package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 *
 * hy_inbound 特产库存表
 *
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Data
public class Inbound {
    private Long id;
    private Long purchaseItemId;
    private Long specialtySpecificationId;
    private String depotCode;
    private Date productDate;
    private Integer durabilityPeriod;
    private Integer inboundNumber;
    private Date updateTime;
    private Integer saleNumber;
    private Date expiration;
}
