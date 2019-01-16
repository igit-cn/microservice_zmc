package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Receiver;

import java.util.List;

/**
 * Created by xyy on 2019/1/16.
 *
 * @author xyy
 */
public interface ReceiverService {
    /**
     * 根据wechatId获取收货人列表
     * */
    List<Receiver> getReceriverListByWechatId(Long wechatId) throws Exception;
    /**
     * 更新 是否为默认收货地址 属性
     * */
    void updateIsDefaultRecieverAddress(Receiver r);

    /** 新建Receiver*/
    void save(Receiver receiver);

    /** 将wechat_id之外的属性进行更新*/
    void updateExceptWechatId(Receiver receiver);

    /** 删除收货地址 TODO 不宜采用直接删除的方式,而应该采用标志位*/
    void delete(Long id);
}
