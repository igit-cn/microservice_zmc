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

    @Insert("INSERT INTO hy_business_order_refund(business_order_id, deliver_type, refund_reson, state, refund_apply_time, refund_accept_time, inbound_time, ship_time, return_complete_time,   wechat_id, rrefund_amount, qrefund_amount, refund_amount, erefund_amount, refund_ship_fee, refund_totalamount, is_delivered, responsible_party) VALUES(#{businessOrderId}, #{deliverType}, #{refundReason}, #{state}, #{refundApplyTime}, #{refundAcceptTime}, #{inboundTime}, #{shipTime}, #{returnCompleteTime}, #{wechatId}, #{rrefundAmount}, #{qrefundAmount}, #{refundAmount}, #{erefundAmount}, #{refundShipFee}, #{refundTotalamount}, #{isDelivered}, #{responsibleParty})")
    void insert(BusinessOrderRefund bRefund);
}
