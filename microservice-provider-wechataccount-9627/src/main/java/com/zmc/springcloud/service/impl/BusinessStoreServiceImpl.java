package com.zmc.springcloud.service.impl;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.BusinessStore;
import com.zmc.springcloud.mapper.BusinessStoreMapper;
import com.zmc.springcloud.service.BusinessStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@Service
public class BusinessStoreServiceImpl implements BusinessStoreService{
    @Autowired
    private BusinessStoreMapper businessStoreMapper;

    @Override
    public BusinessStore getBusinessStoreById(Long id) throws Exception {
        return businessStoreMapper.findBusinessStoreById(id);
    }
}
