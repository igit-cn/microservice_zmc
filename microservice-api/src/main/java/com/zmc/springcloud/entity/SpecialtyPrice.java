package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_specialty_price 特产价格表
 *
 * Created by xyy on 2018/12/10.
 *
 * @author xyy
 */
@Data
public class SpecialtyPrice {
    private Long id;
    private Long specialtyId;
    private Long specificationId;
    private BigDecimal marketPrice;
    private BigDecimal platformPrice;
    private BigDecimal costPrice;
    private Boolean isActive;
    private Date createTime;
    private Date deadTime;
    private String creatorName;
    private BigDecimal storeDivide;
    private BigDecimal exterStoreDivide;
    private BigDecimal businessPersonDivide;
    private BigDecimal deliverPrice;
}
