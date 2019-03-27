package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtyAppraise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /** 根据specialtyid获取isShow isValid的SpecialtyAppraise的列表*/
    @Select("SELECT * FROM hy_specialty_appraise WHERE specialty_id = #{specialtyId} AND is_show = 1 AND is_valid = 1 ORDER BY appraise_time DESC")
    List<SpecialtyAppraise> findSpecialtyAppraiseBySpecialtyId(Long specialtyId);
}
