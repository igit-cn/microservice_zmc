package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderItemMapper {
    /** 根据orderId获取订单条目的list*/
    @Select("SELECT * FROM hy_business_order_item WHERE order_id = #{orderId}")
    List<BusinessOrderItem> findListByOrderId(Long orderId);
}
