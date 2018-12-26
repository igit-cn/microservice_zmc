package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Specialty;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/12/3.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyMapper {
    @Select("SELECT * FROM hy_specialty WHERE id = #{id}")
    Specialty findById(Long id);
}
