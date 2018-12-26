package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * hy_business_order_item 订单条目表
 * Created by xyy on 2018/12/5.
 * @author xyy
 */
@Data
public class BusinessOrderItem {
    private Long id;
    private Long orderId;
    /** 0:普通商品  其他:组合优惠*/
    private Integer type;
    private Long specialtyId;
    private Long specialtySpecificationId;
    private Long purchaseItemId;
    private Integer quantity;
    private Integer outboundQuantity;
    private Date outboundTime;
    /**BusinessOrdrItem  是否退货*/
    private Integer isDelivered;
    private String operator;
    private Integer returnQuantity;
    private BigDecimal salePrice;
    private BigDecimal originalPrice;
    private Long promotionId;
    private Boolean isappraised;
    /**发货商名称*/
    private String deliverName;
    /**发货商类型*/
    private Integer deliverType;
    /**仓库名称*/
    private String depotName;
    private Date createTime;
    /**售后计损数量*/
    private Double lost1Quantity;
    /**库管计损数量*/
    private Double lost2Quantity;
    /**是否是赠品*/
    private Boolean isGift;
}
