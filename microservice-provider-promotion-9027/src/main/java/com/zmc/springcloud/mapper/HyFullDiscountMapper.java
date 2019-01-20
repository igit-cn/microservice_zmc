package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyFullDiscount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Mapper
public interface HyFullDiscountMapper {
    /** 根据promotionId获取HyFullDiscount的list*/
    @Select("SELECT * FROM hy_full_discount WHERE promotion_id = #{promotionId}")
    List<HyFullDiscount> getHyFullDiscountListByPromotionId(Long promotionId);
}
