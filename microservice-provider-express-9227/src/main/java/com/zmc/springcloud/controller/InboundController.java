package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.Inbound;
import com.zmc.springcloud.service.InboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@RestController
public class InboundController {
    @Autowired
    private InboundService inboundService;

    @RequestMapping("/inbound/list/quantity")
    public List<Inbound> getInboundListBySpecificationId(Long specificationId, Integer quantity) throws Exception{
        return inboundService.getInboundListBySpecificationId(specificationId, quantity);
    }

    @RequestMapping("/inbound/depotcode")
    public List<Inbound> getListBySpecificationIdAndDepotCode(Long specificationId, String depotCode) throws Exception{
        return inboundService.getListBySpecificationIdAndDepotCode(specificationId, depotCode);
    }
}
