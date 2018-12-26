package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hy_business_banner
 * <p>
 * Created by xyy on 2018/12/12.
 *
 * @author xyy
 */
@Data
public class BusinessBanner {
    public enum BannerType {
        活动,
        产品,
        广告
    }

    private Long id;
    private String title;
    private String img;
    private BannerType type;
    private Long targetId;
    private Integer orders;
    private Boolean state;
    private String link;
    private Date startTime;
    private Date endTime;
    private BigDecimal pvPrice;
    private BigDecimal uvPrice;
    private Date createDate;
    private Date modifyDate;
    private String creator;
    /**
     * 是普通优惠还是组合优惠
     */
    private Integer isCheck;
}
