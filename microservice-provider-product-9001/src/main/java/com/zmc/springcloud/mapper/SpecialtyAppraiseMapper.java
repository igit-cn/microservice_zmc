package com.zmc.springcloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyAppraiseMapper {
    /** 计算某个特产的评价的数目*/
    @Select("SELECT COUNT(*) FROM hy_specialty_appraise WHERE specialty_id = #{specialtyId}")
    Long count(Long specialtyId);
}
