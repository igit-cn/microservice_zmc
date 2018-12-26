package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * hy_ship
 *
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Data
public class Ship {
    private Long id;
    private Long orderId;
    private Integer type;
    private String deliverOperator;
    private String shipCode;
    private String shipCompany;
    private Date recordTime;
    private String deliveror;
}
