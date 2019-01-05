package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface HyPromotionMapper {
    @Results({@Result(property = "promotionType", column = "promotion_type", typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property = "promotionRule", column = "promotion_rule", typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property = "status", column = "status", typeHandler = EnumOrdinalTypeHandler.class)})
    @Select("SELECT * FROM hy_promotion WHERE id = #{id}")
    HyPromotion findById(Long id);
}
