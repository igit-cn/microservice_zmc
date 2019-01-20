package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 满赠表 hy_full_present
 *
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Data
public class HyFullPresent {
    private Long id;
    private Long promotionId;
    private Integer fullPresentProductNumber;
    private Long fullPresentProductSpecification;
    private Long fullPresentProduct;
    private BigDecimal fullPresentRequirenment;
}
