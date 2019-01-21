package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderRefund;

import java.util.List;

/**
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
public interface BusinessOrderRefundService {
    List<BusinessOrderRefund> getListByBusinessOrderId(Long id) throws Exception;
    /** 保存退款订单*/
    void save(BusinessOrderRefund bRefund);
}
