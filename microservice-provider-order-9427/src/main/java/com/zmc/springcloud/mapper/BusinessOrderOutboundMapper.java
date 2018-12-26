package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderOutbound;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderOutboundMapper {
    @Select("SELECT * FROM hy_business_order_outbound WHERE order_item_id = #{id}")
    List<BusinessOrderOutbound> findListByBusinessOrderItemId(Long id);
}
