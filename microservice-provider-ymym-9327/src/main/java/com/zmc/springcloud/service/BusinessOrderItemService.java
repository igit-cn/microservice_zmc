package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderItem;

import java.util.List;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
public interface BusinessOrderItemService {
    List<BusinessOrderItem> getBusinessOrderItemListByOrderId(Long orderId);
}
