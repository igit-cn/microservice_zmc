package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Provider;
import com.zmc.springcloud.mapper.ProviderMapper;
import com.zmc.springcloud.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyy
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProviderList() throws Exception {
        return providerMapper.findListProvider(true, null, null, null);
    }

    @Override
    public Provider getProvider(Long providerId) throws Exception{
        return providerMapper.findById(providerId);
    }
}
