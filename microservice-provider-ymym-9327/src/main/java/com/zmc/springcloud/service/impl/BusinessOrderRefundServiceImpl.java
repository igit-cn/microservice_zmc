package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessOrderRefund;
import com.zmc.springcloud.mapper.BusinessOrderRefundMapper;
import com.zmc.springcloud.service.BusinessOrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
@Service
public class BusinessOrderRefundServiceImpl implements BusinessOrderRefundService{
    @Autowired
    private BusinessOrderRefundMapper businessOrderRefundMapper;

    @Override
    public List<BusinessOrderRefund> getListByBusinessOrderId(Long id) throws Exception {
        return businessOrderRefundMapper.findListByBusinessOrderId(id);
    }

    @Override
    public void save(BusinessOrderRefund bRefund) {
        businessOrderRefundMapper.insert(bRefund);
    }
}
