package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtySpecification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtySpecificationMapper {
    @Select("SELECT * FROM hy_specialty_specification WHERE id = #{id}")
    SpecialtySpecification findById(Long id);
    @Update("UPDATE hy_specialty_specification SET base_inbound = #{baseInbound}, has_sold = #{hasSold}")
    void updateInboundAndHasSold(SpecialtySpecification specification);
}
