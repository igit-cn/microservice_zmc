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

    BusinessOrderItem getById(Long id) throws Exception;
    /** 为订单条目指定订单id*/
    void setBusinessOrder(BusinessOrderItem item, Long orderId) throws Exception;
}
