package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtyAppraiseImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyAppraiseImageMapper {
    /** 根据specialtyAppraisedId获取评论图片列表*/
    @Select("SELECT * FROM hy_specialty_appraise_image WHERE appraise_id = #{specialtyAppraiseId}")
    List<SpecialtyAppraiseImage> findListBySpecialtyAppraiseId(Long specialtyAppraiseId);
}
