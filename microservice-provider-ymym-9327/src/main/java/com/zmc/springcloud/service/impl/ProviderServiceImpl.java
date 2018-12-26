package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Provider;
import com.zmc.springcloud.mapper.ProviderMapper;
import com.zmc.springcloud.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;
    @Override
    public Provider find(Long providerId)throws Exception {
        return providerMapper.findById(providerId);
    }
}
