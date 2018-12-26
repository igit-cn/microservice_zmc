package com.zmc.springcloud.entity;

import lombok.Data;

/**
 * hy_groupitem_promotion_detail
 *
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Data
public class HyGroupitemPromotionDetail {
    private Long id;
    private Integer buyNumber;
    private Long groupItemPromotionId;
    private Long itemId;
    private Long itemSpecificationId;
}
