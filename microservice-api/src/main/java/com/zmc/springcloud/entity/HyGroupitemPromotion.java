package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * 组合优惠 hy_groupitem_promotion
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Data
public class HyGroupitemPromotion {
    private Long id;
    private Long promotionId;
    private BigDecimal sellPrice;
    private BigDecimal marketPrice;
    private Integer limitedNum;
    private Long storeDivide;
    private Long exterStoreDivide;
    private Long businessPersonDivide;
    private Integer havePromoted;
    private Integer promoteNum;
}
