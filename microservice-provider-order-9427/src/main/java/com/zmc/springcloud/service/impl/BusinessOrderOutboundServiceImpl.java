package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.BusinessOrderOutbound;
import com.zmc.springcloud.mapper.BusinessOrderOutboundMapper;
import com.zmc.springcloud.service.BusinessOrderOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
@Service
public class BusinessOrderOutboundServiceImpl implements BusinessOrderOutboundService{

    @Autowired
    private BusinessOrderOutboundMapper businessOrderOutboundMapper;

    @Override
    public List<BusinessOrderOutbound> getListByBusinessOrderItemId(Long id) throws Exception {
        return businessOrderOutboundMapper.findListByBusinessOrderItemId(id);
    }
}
