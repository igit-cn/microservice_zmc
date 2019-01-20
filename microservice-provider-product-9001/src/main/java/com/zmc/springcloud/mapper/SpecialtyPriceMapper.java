package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.SpecialtyPrice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * Created by xyy on 2018/12/10.
 *
 * @author xyy
 */
@Mapper
public interface SpecialtyPriceMapper {
    /**
     * 通过特产id筛选isActive的特产价格
     */
    @Select("SELECT * FROM hy_specialty_price WHERE specialty_id = #{specialtyId} AND is_active = #{isActive}")
    SpecialtyPrice find(@Param("specialtyId") Long specialtyId, @Param("isActive") boolean isActive);

    @Insert("INSERT INTO hy_specialty_price(specialty_id, specification_id, market_price, platform_price, cost_price, is_active, create_time, creator_name, store_divide, exter_store_divide, business_person_divide, deliver_price)  VALUES(#{specialtyId}, #{specificationId}, #{marketPrice}, #{platformPrice}, #{costPrice}, #{isActive}, #{createTime}, #{creatorName}, #{storeDivide}, #{exterStoreDivide}, #{businessPersonDivide}, #{deliverPrice})")
    void insert(SpecialtyPrice specialtyPrice);

    /** 更新SpecialtyPrice 注意只修改is_active和dead_time*/
    @Update("UPDATE hy_specialty_price SET is_active = 0, dead_time = NOW() WHERE id = #{id}")
    void updateSpecialtyPrice(Long id);
}
