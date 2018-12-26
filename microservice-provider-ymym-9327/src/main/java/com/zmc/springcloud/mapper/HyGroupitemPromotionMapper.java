package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyGroupitemPromotion;
import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface HyGroupitemPromotionMapper {
    @Select("SELECT * FROM hy_groupitem_promotion WHERE id = #{id}")
    HyGroupitemPromotion find(long id);

    @Select("SELECT * FROM hy_groupitem_promotion_detail WHERE group_item_promotion_id = #{groupItemPromotionId}")
    List<HyGroupitemPromotionDetail> findListByGroupItemPromotionId(Long groupItemPromotionId);

    @Update("UPDATE hy_groupitem_promotion SET promote_num = #{promoteNum}, have_promoted = #{havePromoted} WHERE id = #{id}")
    void updatePromotion(HyGroupitemPromotion groupitemPromotion);
}
