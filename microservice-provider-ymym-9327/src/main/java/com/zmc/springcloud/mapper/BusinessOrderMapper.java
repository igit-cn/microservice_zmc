package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.BusinessOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /** 更新订单退款金额和订单状态*/
    @Update("UPDATE hy_business_order SET refound_money = #{refoundMoney}, order_state = #{orderState} WHERE id = #{id}")
    void updateRefundMoneyAndOrderState(BusinessOrder bOrder);

    /** 更新订单收货时间和订单状态*/
    @Update("UPDATE hy_business_order SET receive_time = #{receiveTime}, order_state = #{orderState} WHERE id = #{id}")
    void updateReceiveTimeAndOrderState(BusinessOrder businessOrder);

    /** 更新订单的isValid状态*/
    @Update("UPDATE hy_business_order SET is_valid = #{isValid} WHERE id = #{id}")
    void updateIsValid(BusinessOrder order);

    /** 更新订单的取消时间和订单状态*/
    @Update("UPDATE hy_business_order SET order_cancel_time = #{orderCancelTime}, order_state = #{orderState} WHERE id = #{id}")
    void updateOrderCancelTimeAndOrderState(BusinessOrder businessOrder);
}
