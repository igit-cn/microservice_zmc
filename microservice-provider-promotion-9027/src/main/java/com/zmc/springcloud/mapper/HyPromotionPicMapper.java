package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyPromotionPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/1/19.
 *
 * @author xyy
 */
@Mapper
public interface HyPromotionPicMapper {
    @Select("SELECT * FROM hy_promotion_pic WHERE promotion_id = #{promotionId}")
    List<HyPromotionPic> getHyPromotionListByPromotionId(Long promotionId);
}
