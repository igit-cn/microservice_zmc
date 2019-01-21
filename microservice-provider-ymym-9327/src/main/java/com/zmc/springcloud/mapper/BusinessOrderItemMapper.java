package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderItemMapper {
    @Select("SELECT * FROM hy_business_order_item WHERE id = #{id}")
    BusinessOrderItem findById(Long id);

    /** 根据orderId获取订单条目的list*/
    @Select("SELECT * FROM hy_business_order_item WHERE order_id = #{orderId}")
    List<BusinessOrderItem> findListByOrderId(Long orderId);

    /** 获取某个orderId的退货数量不为0的订单条目的list*/
    @Select("SELECT * FROM hy_business_order_item WHERE order_id = #{orderId} AND return_quantity > #{returnQuantity}")
    List<BusinessOrderItem> getListByOrderIdAndReturnQuantity(@Param("orderId")Long orderId, @Param("returnQuantity")Integer returnQuantity);

    @Insert("INSERT INTO hy_business_order_item(order_id, type, specialty_id, specialty_specification_id, quantity, return_quantity, sale_price, original_price, promotion_id, isappraised, deliver_name, deliver_type, create_time, lost1_quantity, lost2_quantity, is_gift) VALUES(#{orderId}, #{type}, #{specialtyId}, #{specialtySpecificationId}, #{quantity}, 0, #{salePrice}, #{originalPrice}, #{promotionId}, #{isappraised}, #{deliverName}, #{deliverType}, NOW(), 0, 0, #{isGift})")
    void insert(BusinessOrderItem businessOrderItem);
}
