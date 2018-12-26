package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderItemMapper {
    @Insert("INSERT INTO hy_business_order_item(order_id, type, specialty_id, specialty_specification_id, quantity, return_quantity, sale_price, original_price, promotion_id, isappraised, deliver_name, deliver_type, create_time, lost1_quantity, lost2_quantity, is_gift) VALUES(#{orderId}, #{type}, #{specialtyId}, #{specialtySpecificationId}, #{quantity}, 0, #{salePrice}, #{originalPrice}, #{promotionId}, #{isappraised}, #{deliverName}, #{deliverType}, NOW(), 0, 0, #{isGift})")
    void insert(BusinessOrderItem businessOrderItem);
    @Select("SELECT * FROM hy_business_order_item WHERE order_id = #{orderId}")
    List<BusinessOrderItem> findListByOrderId(Long orderId);
}
