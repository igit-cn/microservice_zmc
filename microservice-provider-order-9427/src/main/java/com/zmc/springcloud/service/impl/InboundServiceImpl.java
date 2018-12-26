package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Inbound;
import com.zmc.springcloud.mapper.InboundMapper;
import com.zmc.springcloud.service.InboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class InboundServiceImpl implements InboundService{
    @Autowired
    private InboundMapper inboundMapper;

    @Override
    public List<Inbound> getInboundListBySpecificationId(Long specificationId, Integer i) throws Exception {
        return inboundMapper.findListInbound(specificationId, i);
    }

    @Override
    public List<Inbound> getListBySpecificationIdAndDepotCode(Long specificationId, String depotCode) throws Exception {
        return inboundMapper.findListBySpecificationIdAndDepotCode(specificationId, depotCode);
    }
}
