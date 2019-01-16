package com.zmc.springcloud.mapper;

import com.zmc.springcloud.entity.Receiver;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xyy on 2019/1/16.
 *
 * @author xyy
 */
@Mapper
public interface ReceiverMapper {
    /**
     * 根据wechatId获取收货人列表
     * TODO wechatId这里是String而不是Long
     * */
    @Select("SELECT * FROM hy_receiver WHERE wechat_id = #{wechatId}")
    List<Receiver> getReceriverListByWechatId(Long wechatId);

    /**
     * 更新 是否为默认收货地址 属性
     * */
    @Update("UPDATE hy_receiver SET is_default_receiver_address = #{isDefaultReceiverAddress} WHERE id = #{id}")
    void updateIsDefaultRecieverAddress(Receiver r);

    /** 新建Receiver*/
    @Insert("INSERT INTO hy_receiver(is_default_receiver_address, receiver_address, receiver_mobile, receiver_name, wechat_id, is_vip_address) VALUES(#{isDefaultReceiverAddress}, #{receiverAddress}, #{receiverMobile}, #{receiverName}, #{wechatId}, #{isVipAddress})")
    void insert(Receiver receiver);

    /** 更新除wechat_id之外的属性*/
    @Update("UPDATE hy_receiver SET is_default_receiver_address = #{isDefaultReceiverAddress}, receiver_address = #{receiverAddress}, receiver_mobile = #{receiverMobile}, receiver_name = #{receiverName} WHERE id = #{id}")
    void updateExceptWechatId(Receiver receiver);

    /** 删除收货地址 TODO 不宜采用直接删除的方式,而应该采用标志位*/
    @Delete("DELETE FROM hy_receiver WHERE id = #{id}")
    void delete(Long id);
}
