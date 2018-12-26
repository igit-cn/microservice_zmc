package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * 优惠明细 hy_singleitem_promotion
 *
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Data
public class HySingleitemPromotion {
    private Long id;
    private Long promotionId;
    private Long specialtyId;
    private Long specificationId;
    private Integer limitedNum;
    private Long storeDivide;
    private Long exterStoreDivide;
    private Long businessPersonDivide;
    private Integer havePromoted;
    private Integer promoteNum;
}
