package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Specialty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyMapper {
    @Select("SELECT * FROM hy_specialty WHERE id = #{specialtyId}")
    Specialty findById(Long specialtyId);
}
