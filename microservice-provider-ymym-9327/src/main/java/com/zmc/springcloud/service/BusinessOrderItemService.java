package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrder;
import com.zmc.springcloud.entity.BusinessOrderItem;

import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
public interface BusinessOrderItemService {
    /** 根据Id获取订单条目*/
    BusinessOrderItem getById(Long id) throws Exception;

    List<BusinessOrderItem> getBusinessOrderItemListByOrderId(Long orderId) throws Exception;

    List<Map<String,Object>> getRefundItemMapList(BusinessOrder order) throws Exception;

    /** 保存订单条目*/
    void save(BusinessOrderItem businessOrderItem)throws Exception;
}
