package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface HyPromotionMapper {
    @Select("SELECT * FROM hy_promotion WHERE id = #{id}")
    HyPromotion find(Long id);
}
