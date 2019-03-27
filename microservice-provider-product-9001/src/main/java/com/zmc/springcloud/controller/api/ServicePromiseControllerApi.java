package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.ServicePromise;
import com.zmc.springcloud.service.ServicePromiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/3/27.
 *
 * @author xyy
 */
@RestController
public class ServicePromiseControllerApi {
    @Autowired
    private ServicePromiseService servicePromiseService;

    /** 获取客户端 服务承诺*/
    @GetMapping("/service/promise")
    public List<ServicePromise> getServicePromise() throws Exception{
        return servicePromiseService.getServicePromise();
    }
}
