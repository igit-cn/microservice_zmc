package com.zmc.springcloud.feignclient.express;

import com.zmc.springcloud.entity.HyVinbound;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-express")
public interface HyVinboundFeignClient {
    @RequestMapping("/vinbound/{id}")
    HyVinbound getHyVinboundBySpecificationId(@PathVariable("id") Long id);

    @RequestMapping("/vinbound/add")
    void addHyVinbound(@RequestBody HyVinbound hyVinbound);

    @RequestMapping("/vinbound/update")
    void updateHyVinbound(@RequestBody HyVinbound hyVinbound);

    @RequestMapping("/vinbound/list/{id}")
    List<HyVinbound> getHyVinboundListBySpecificationId(@PathVariable("id") Long id);
}