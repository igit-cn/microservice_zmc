package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.WeDivideModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Mapper
public interface WeDivideModelMapper {
    @Select("SELECT * FROM hy_we_divide_model WHERE type = #{modelType} AND is_valid = #{isValid}")
    List<WeDivideModel> findWeDivideModelListByModelTypeAndIsValid(@Param("modelType") String modelType, @Param("isValid")Boolean isValid);
}
