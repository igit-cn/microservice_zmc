package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.OrderTransaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Mapper
public interface OrderTransactionMapper {
    @Insert("INSERT INTO hy_order_transaction(serial_num, order_id, wechat_balance, order_coupon, payment, pay_type, pay_account, pay_flow, pay_time) VALUES(#{serialNum}, #{orderId}, #{wechatBalance}, #{orderCoupon}, #{payment}, #{payType}, #{payAccount}, #{payFlow}, #{payTime})")
    void insert(OrderTransaction transaction);
}
