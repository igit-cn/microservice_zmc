package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderRefund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderRefundMapper {
    @Select("SELECT * FROM hy_business_order_refund WHERE business_order_id = #{businessOrderId}")
    List<BusinessOrderRefund> findListByBusinessOrderId(Long businessOrderId);
}
