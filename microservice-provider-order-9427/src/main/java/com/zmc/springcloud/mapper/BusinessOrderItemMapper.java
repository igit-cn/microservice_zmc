package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderItemMapper {
    @Select("SELECT * FROM hy_business_order_item WHERE id = #{id}")
    BusinessOrderItem findById(Long id);

    @Select("SELECT * FROM hy_business_order_item WHERE order_id = #{orderId}")
    List<BusinessOrderItem> findListByOrderId(Long orderId);

    @Update(" UPDATE hy_business_order_item SET order_id = #{orderId} WHERE id = #{orderItemId}")
    void updateOrderId(@Param("orderItemId") Long orderItemId, @Param("orderId") Long orderId);
}
