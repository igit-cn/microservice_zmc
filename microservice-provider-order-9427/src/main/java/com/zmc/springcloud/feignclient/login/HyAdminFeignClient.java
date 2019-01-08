package com.zmc.springcloud.feignclient.login;

import com.zmc.springcloud.entity.HyAdmin;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-login")
public interface HyAdminFeignClient {
    @RequestMapping(value = "/admin/{username}")
    HyAdmin getHyAdminByUserName(@PathVariable("username") String username);

    @RequestMapping(value = "/admin/add")
    void addHyAdmin(HyAdmin hyAdmin);
}
