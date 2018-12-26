package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderItem;

import java.util.List;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
public interface BusinessOrderItemService {
    void save(BusinessOrderItem businessOrderItem)throws Exception;
    /** 根据订单id获取BusinessOrderItem的列表*/
    List<BusinessOrderItem> getListByOrderId(Long id)throws Exception;
}
