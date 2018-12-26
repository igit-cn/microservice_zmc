package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xyy on 2018/12/8.
 *
 * @author xyy
 */
@Mapper
public interface HyAreaMapper {
    @Select("SELECT * FROM hy_area WHERE id = #{areaId}")
    HyArea findById(Long areaId);
}
