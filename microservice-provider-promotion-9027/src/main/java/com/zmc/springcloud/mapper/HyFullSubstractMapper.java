package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HyFullSubstract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Mapper
public interface HyFullSubstractMapper {
    /** 根据promotionId获取HyFullSubstract的list*/
    @Select("SELECT * FROM hy_full_substract WHERE promotion_id = #{promotionId}")
    List<HyFullSubstract> getHyFullSubstractListByPromotionId(Long promotionId);
}
