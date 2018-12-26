package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by xyy on 2018/12/14.
 *
 * @author xyy
 */
@Mapper
public interface BusinessOrderMapper {
    @Insert("INSERT INTO hy_business_order(order_code, order_phone, order_wechat_id, webusiness_id, total_money, promotion_amount, ship_fee, shouldpay_money, coupon_money, balance_money, pay_money, order_state, order_time, receiver_name, receiver_phone, receiver_address, receiver_remark, receive_type, coupon_id,  promotion_id, is_balanced, is_divided, is_show, is_appraised, is_balance, is_valid) VALUES(#{orderCode}, #{orderPhone}, #{orderWechatId}, #{weBusinessId}, #{totalMoney}, #{promotionAmount}, #{shipFee}, #{shouldpayMoney}, #{couponMoney}, #{balanceMoney}, #{payMoney}, #{orderState}, #{orderTime}, #{receiverName}, #{receiver_phone}, #{receiverAddress}, #{receiverRemark}, #{receiveType}, #{couponId}, #{promotionId}, #{isBalanced}, #{isDivided}, #{isShow}, #{isAppraised}, #{isBalance}, #{isValid})")
    void save(BusinessOrder businessOrder);
    @Select("SELECT * FROM hy_business_order WHERE order_code = #{orderCode}")
    BusinessOrder getByOrderCode(String orderCode);
    @Update("UPDATE hy_business_order SET order_state = #{orderState}, pay_time = #{payTime} WHERE id = #{id}")
    void updateOrderStateAndPayTime(BusinessOrder businessOrder);
}
