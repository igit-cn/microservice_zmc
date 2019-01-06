package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.OrderTransaction;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
public interface OrderTransactionService {
    /** 保存交易*/
    void save(OrderTransaction transaction)throws Exception;
}
