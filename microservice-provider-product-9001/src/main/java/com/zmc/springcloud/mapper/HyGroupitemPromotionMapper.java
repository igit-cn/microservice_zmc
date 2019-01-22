package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface HyGroupitemPromotionMapper {
    @Select("SELECT * FROM hy_groupitem_promotion WHERE id = #{id}")
     HyGroupitemPromotion findById(Long id);

    /** 更新promotionNum和havePromoted*/
    @Update("UPDATE hy_groupitem_promotion SET promote_num = #{promoteNum}, have_promoted = #{havePromoted} WHERE id = #{id}")
    void updateHyGroupitemPromotion(HyGroupitemPromotion groupitemPromotion);
}
