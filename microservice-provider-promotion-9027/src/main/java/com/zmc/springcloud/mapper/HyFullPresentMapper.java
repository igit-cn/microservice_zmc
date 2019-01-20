package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyFullPresent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Mapper
public interface HyFullPresentMapper {
    /** 根据promotionId获取HyFullPresent的list*/
    @Select("SELECT * FROM hy_full_present WHERE promotion_id = #{promotionId}")
    List<HyFullPresent> getHyFullPresentListByPromotionId(Long promotionId);
}
