package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * 促销  hy_promotion
 *
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Data
public class HyPromotion {
    public enum PromotionType {
        普通优惠,
        组合优惠
    }

    public enum PromotionRule {
        满减,
        满赠,
        满折
    }

    public enum PromotionStatus {
        已取消,
        未开始,
        进行中,
        已结束
    }
    private Long id;
    private String promotionName;
    private PromotionType promotionType;
    private PromotionRule promotionRule;
    private Date promotionStarttime;
    private Date promotionEndtime;
    private PromotionStatus status;
    private String introduction;
    private String content;
    private Integer orders;
    private Boolean couponAvailable;
    private Date createTime;
    private Long creator;
    private Date deadTime;
    private Boolean isBanner;
}
