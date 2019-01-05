package com.zmc.springcloud.feignclient.express;

import com.zmc.springcloud.entity.HyVinbound;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-express")
public interface HyVinboundFeignClient {
    @RequestMapping("/vinbound/{id}")
    HyVinbound getHyVinboundBySpecificationId(@PathVariable Long id);

    @RequestMapping("/vinbound/add")
    void addHyVinbound(HyVinbound hyVinbound);

    @RequestMapping("/vinbound/update")
    void updateHyVinbound(HyVinbound hyVinbound);

    @RequestMapping("/vinbound/updatenum")
    void updateHyVinboundNum(Long id, Integer saleNumber, Integer vinboundNumber);

    @RequestMapping("/vinbound/list/{id}")
    List<HyVinbound> getHyVinboundListBySpecificationId(@PathVariable Long id);
}