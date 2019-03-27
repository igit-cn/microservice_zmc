package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Data
public class WeDivideModel {
    private Long id;
    private String modelType;
    private BigDecimal store;
    private BigDecimal weBusiness;
    private BigDecimal introducer;
    private Date createTime;
    private Date endTime;
    private String operator;
    private Boolean isValid;
}
