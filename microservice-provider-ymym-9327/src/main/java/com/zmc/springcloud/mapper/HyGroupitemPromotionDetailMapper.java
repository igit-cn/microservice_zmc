package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyGroupitemPromotionDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface HyGroupitemPromotionDetailMapper {
    @Select("SELECT * FROM hy_groupitem_promotion_detail WHERE group_item_promotion_id = #{groupItemPromotionId}")
    List<HyGroupitemPromotionDetail> findListByGroupItemPromotionId(Long groupItemPromotionId);
}
