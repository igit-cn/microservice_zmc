package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.ServicePromise;
import com.zmc.springcloud.mapper.ServicePromiseMapper;
import com.zmc.springcloud.service.ServicePromiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/3/27.
 *
 * @author xyy
 */
@Service
public class ServicePromiseServiceImpl implements ServicePromiseService {
    @Autowired
    private ServicePromiseMapper servicePromiseMapper;

    @Override
    public List<ServicePromise> getServicePromise() throws Exception {
        return servicePromiseMapper.findAll();
    }
}
