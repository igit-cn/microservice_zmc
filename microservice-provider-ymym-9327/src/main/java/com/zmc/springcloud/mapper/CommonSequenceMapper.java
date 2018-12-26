package com.zmc.springcloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface CommonSequenceMapper {
    /**
     * 获取某种序列号
     */
    @Select("SELECT value FROM hy_common_sequence WHERE type = #{type}")
    Long findValueByType(Integer type);

    /**
     * 更新序列号的值
     */
    @Update("UPDATE hy_common_sequence SET value = #{value} WHERE type = #{type}")
    void updateValue(@Param("type") Integer type, @Param("value") Long value);
}
