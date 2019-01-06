package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessOrderItem;
import com.zmc.springcloud.mapper.BusinessOrderItemMapper;
import com.zmc.springcloud.service.BusinessOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class BusinessOrderItemServiceImpl implements BusinessOrderItemService {
    @Autowired
    private BusinessOrderItemMapper businessOrderItemMapper;

    @Override
    public List<BusinessOrderItem> getBusinessOrderItemListByOrderId(Long orderId) throws Exception {
        return businessOrderItemMapper.findListByOrderId(orderId);
    }

    @Override
    public BusinessOrderItem getById(Long id) throws Exception {
        return businessOrderItemMapper.findById(id);
    }

    @Override
    public void setBusinessOrder(BusinessOrderItem item, Long orderId) throws Exception {
        businessOrderItemMapper.updateOrderId(item.getId(), orderId);
    }

    @Override
    public void save(BusinessOrderItem businessOrderItem) throws Exception {
        businessOrderItemMapper.insert(businessOrderItem);
    }

    @Override
    public List<BusinessOrderItem> getListByOrderId(Long orderId) throws Exception {
        return businessOrderItemMapper.findListByOrderId(orderId);
    }

}
