package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderItem;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface BusinessOrderItemService {
    List<BusinessOrderItem> getBusinessOrderItemListByOrderId(Long orderId) throws Exception;
    /** 根据Id获取订单条目*/
    BusinessOrderItem getById(Long id) throws Exception;
    /** 为订单条目指定订单id*/
    void setBusinessOrder(BusinessOrderItem item, Long orderId) throws Exception;

    /** 保存订单条目*/
    void save(BusinessOrderItem businessOrderItem)throws Exception;
    /** 根据订单id获取BusinessOrderItem的列表*/
    List<BusinessOrderItem> getListByOrderId(Long id)throws Exception;

}
