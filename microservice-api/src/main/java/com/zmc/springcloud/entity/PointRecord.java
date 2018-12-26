package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_pointrecord  积分
 *
 * Created by xyy on 2018/12/17.
 *
 * @author xyy
 */
@Data
public class PointRecord {
    private Long id;
    private Long wechatAccount;
    /** 积分变化值*/
    private Integer changevalue;
    private String reason;
    /** 兑换金额*/
    private BigDecimal balance;
    private Date createTime;
}
