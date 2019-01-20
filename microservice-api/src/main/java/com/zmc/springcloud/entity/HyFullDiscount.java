package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 满折表 hy_full_discount
 *
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Data
public class HyFullDiscount {
    private Long id;
    private Long promotionId;
    private BigDecimal discountOff;
    private BigDecimal discountRequirenment;
}
