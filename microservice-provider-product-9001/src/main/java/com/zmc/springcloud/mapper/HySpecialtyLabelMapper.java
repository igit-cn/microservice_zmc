package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HySpecialtyLabel;
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
public interface HySpecialtyLabelMapper {
    /** 根据标签id 产品id和isMarked筛选HySpecialtyLabel的列表*/
    @Select("SELECT * FROM hy_specialty_label WHERE label_id = #{labelId} AND specialty_id = #{specialtyId} AND is_marked = #{isMarked}")
    List<HySpecialtyLabel> findHySpecialtyLabelList(@Param("labelId")Long labelId, @Param("specialtyId")Long specialtyId, @Param("isMarked")boolean isMarked);
}
