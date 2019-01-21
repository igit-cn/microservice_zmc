package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrderRefund;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("INSERT INTO hy_business_order_refund(business_order_id, deliver_type, refund_reson, state, refund_apply_time, wechat_id, rrefund_amount, qrefund_amount, refund_amount, refund_totalamount) VALUES(#{businessOrderId}, #{deliverType}, #{refundReason}, #{state}, #{refundApplyTime}, #{wechatId}, #{rrefundAmount}, #{qrefundAmount}, #{refundAmount}, #{refundTotalamount}) ")
    void insert(BusinessOrderRefund bRefund);
}
