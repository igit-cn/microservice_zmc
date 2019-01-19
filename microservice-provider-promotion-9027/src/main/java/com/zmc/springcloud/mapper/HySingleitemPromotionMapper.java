package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.HySingleitemPromotion;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface HySingleitemPromotionMapper {
    /** 筛选HySingleitemPromotion的列表  注意按id降序*/
    @SelectProvider(type = Provider.class, method = "findList")
    List<HySingleitemPromotion> findList(Long specialtySpecificationId, Long promotionId);
    class Provider{
        public String findList(Long specialtySpecificationId, Long promotionId){
            StringBuilder stringBuilder = new StringBuilder("SELECT * FROM hy_singleitem_promotion WHERE 1=1");
            if(specialtySpecificationId != null){
                stringBuilder.append(" AND specification_id = ");
                stringBuilder.append(specialtySpecificationId);
            }
            if(promotionId != null){
                stringBuilder.append(" AND promotion_id = ");
                stringBuilder.append(promotionId);
            }
            stringBuilder.append(" ORDER BY id DESC");
            return stringBuilder.toString();
        }
    }


    @Update("UPDATE hy_singleitem_promotion SET promote_num = #{promoteNum}, have_promoted=#{havePromoted} WHERE id = #{id}")
    void updatePromotion(HySingleitemPromotion singleitemPromotion);
}
