package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.ServicePromise;

import java.util.List;

/**
 * Created by xyy on 2019/3/27.
 *
 * @author xyy
 */
public interface ServicePromiseService {
    /** 获取客户端 服务承诺*/
    List<ServicePromise> getServicePromise() throws Exception;
}
