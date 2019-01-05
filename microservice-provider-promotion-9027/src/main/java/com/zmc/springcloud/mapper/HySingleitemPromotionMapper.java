package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HySingleitemPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface HySingleitemPromotionMapper {
    /** 筛选HySingleitemPromotion的列表  注意按id降序*/
    @Select("SELECT * FROM hy_singleitem_promotion WHERE specification_id = #{specialtySpecificationId} AND promotion_id = #{promotionId} ORDER BY id DESC")
    List<HySingleitemPromotion> findList(@Param("specialtySpecificationId") Long specialtySpecificationId, @Param("promotionId") Long promotionId);
    @Update("UPDATE hy_singleitem_promotion SET promote_num = #{promoteNum}, have_promoted=#{havePromoted} WHERE id = #{id}")
    void updatePromotion(HySingleitemPromotion singleitemPromotion);
}
