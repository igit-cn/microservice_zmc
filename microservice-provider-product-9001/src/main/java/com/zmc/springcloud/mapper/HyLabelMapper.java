package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Mapper
public interface HyLabelMapper {
    /** 在hy_specialty_label根据产品id获取label的id作为条件从hy_label获取列表*/
    @Select("SELECT * FROM hy_label WHERE id IN (SELECT label_id FROM hy_specialty_label WHERE specialty_id = #{specialtyId})")
    List<HyLabel> findListBySpecialtyId(Long specialtyId);
}
