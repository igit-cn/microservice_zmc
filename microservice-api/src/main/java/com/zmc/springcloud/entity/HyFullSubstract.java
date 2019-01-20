package com.zmc.springcloud.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 满减表 hy_full_substract
 *
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Data
public class HyFullSubstract {
    private Long id;
    private Long promotionId;
    private BigDecimal fullFreeAmount;
    private BigDecimal fullFreeRequirement;
}
