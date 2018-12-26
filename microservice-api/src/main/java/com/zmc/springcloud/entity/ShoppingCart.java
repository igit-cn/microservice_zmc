package com.zmc.springcloud.entity;

import lombok.Data;

import java.util.Date;

/**
 * 购物车 shopping_cart
 *
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Data
public class ShoppingCart {
    private Long id;
    private Long userId;
    private Long wechatId;
    private Long specialtyId;
    private Long specialtySpecificationId;
    private Integer quantity;
    private Date addTime;
    private Boolean isGroupPromotion;
}
