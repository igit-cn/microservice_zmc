package com.zmc.springcloud.feignclient.express;

import com.zmc.springcloud.entity.Inbound;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-express")
public interface InboundFeignClient {
    @RequestMapping(value = "/inbound/list/quantity", method = RequestMethod.GET)
    List<Inbound> getInboundListBySpecificationId(@RequestParam("specificationId") Long specificationId, @RequestParam("quantity") Integer quantity);

    @RequestMapping(value = "/inbound/depotcode", method = RequestMethod.GET)
    List<Inbound> getListBySpecificationIdAndDepotCode(@RequestParam("specificationId") Long specificationId, @RequestParam("depotCode") String depotCode);
}
