package com.zmc.springcloud.feignclient.express;

import com.zmc.springcloud.entity.Inbound;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-express")
public interface InboundFeignClient {
    @RequestMapping("/inbound/list/quantity")
    List<Inbound> getInboundListBySpecificationId(Long specificationId, Integer quantity);

    @RequestMapping("/inbound/depotcode")
    List<Inbound> getListBySpecificationIdAndDepotCode(Long specificationId, String depotCode);
}
