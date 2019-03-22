package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Store;
import com.zmc.springcloud.mapper.StoreMapper;
import com.zmc.springcloud.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@Service
public class StoreServiceImpl implements StoreService{
    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Store getStoreById(Long id) throws Exception {
        return storeMapper.findStoreById(id);
    }
}
