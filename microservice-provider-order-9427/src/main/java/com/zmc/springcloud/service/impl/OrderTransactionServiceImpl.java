package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.OrderTransaction;
import com.zmc.springcloud.mapper.OrderTransactionMapper;
import com.zmc.springcloud.service.OrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Service
public class OrderTransactionServiceImpl implements OrderTransactionService{
    @Autowired
    private OrderTransactionMapper orderTransactionMapper;
    @Override
    public void save(OrderTransaction transaction)throws Exception {
        orderTransactionMapper.insert(transaction);
    }
}
