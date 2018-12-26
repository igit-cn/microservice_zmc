package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtySpecification;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by xyy on 2018/12/4.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtySpecificationMapper {
    @Select("SELECT * FROM hy_specialty_specification WHERE id = #{id}")
    SpecialtySpecification findById(Long id);
}
