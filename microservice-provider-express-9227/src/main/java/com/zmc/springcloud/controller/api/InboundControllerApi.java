package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.Inbound;
import com.zmc.springcloud.service.InboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@RestController
public class InboundControllerApi {
    @Autowired
    private InboundService inboundService;

    @RequestMapping("/inbound/list/quantity")
    public List<Inbound> getInboundListBySpecificationId(@RequestParam("specificationId") Long specificationId, @RequestParam("quantity")Integer quantity) throws Exception{
        return inboundService.getInboundListBySpecificationId(specificationId, quantity);
    }

    @RequestMapping("/inbound/depotcode")
    public List<Inbound> getListBySpecificationIdAndDepotCode(@RequestParam("specificationId") Long specificationId, @RequestParam("depotCode")String depotCode) throws Exception{
        return inboundService.getListBySpecificationIdAndDepotCode(specificationId, depotCode);
    }
}
