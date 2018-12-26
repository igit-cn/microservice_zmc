package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 微商城用户微信账户表 hy_wechat_account
 *
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Data
public class WechatAccount {
    private Long id;
    private String wechatName;
    private String wechatOpenid;
    private String phone;
    private Date bindTime;
    private BigDecimal totalbalance;
    private Boolean isActive;
    private Boolean isVip;
    private Date createTime;
    private Boolean isWeBusiness;
    /** 总积分*/
    private Integer totalpoint;
    /** 可用积分*/
    private Integer point;
    /** 是否是新用户*/
    private Boolean isNew;
}
