package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface HyGroupitemPromotionMapper {
    @Select("SELECT * FROM hy_groupitem_promotion WHERE id = #{id}")
     HyGroupitemPromotion findById(Long id);
}
