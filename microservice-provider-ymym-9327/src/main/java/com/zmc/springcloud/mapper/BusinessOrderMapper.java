package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderMapper {
    /** 根据id获取BusinessOrder*/
    @Select("SELECT * FROM hy_business_order WHERE ID = #{id}")
    BusinessOrder findById(Long id);

    @Select("SELECT * FROM hy_business_order WHERE order_wechat_id = #{wechatId} AND order_state = #{status} AND is_valid = #{isValid} AND is_show = #{isShow} ORDER BY order_time DESC")
    List<BusinessOrder> getOrderListByAccount(@Param("wechatId")Long wechatId, @Param("status")Integer status, @Param("isValid")Boolean isValid, @Param("isShow")Boolean isShow);
}
