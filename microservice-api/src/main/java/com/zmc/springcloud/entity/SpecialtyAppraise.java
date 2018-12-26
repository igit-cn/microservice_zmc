package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/** 特产评论*/
@Data
public class SpecialtyAppraise {
    private Long id;
    /**评论内容*/
    private String appraiseContent;
    /**评论时间*/
    private Date appraiseTime;
    /**满意度*/
    private Integer contentLevel;
    /**是否匿名*/
    private Boolean isAnonymous;
    /**是否晒单*/
    private Boolean isShow;
    /**评论更新时间*/
    private Date updateDate;
    /**评论特产*/
    private Long specialtyId;
    /**是否可见*/
    private Boolean isValid;
    /**评论的微信账号*/
    private Long accountId;
    /**删除时间*/
    private Date deleteDate;
    /**特产订单*/
    private Long businessOrderId;
    /**特产订单明细*/
    private Long orderItemId;
    /**评论特产规格*/
    private Long specificationId;

}