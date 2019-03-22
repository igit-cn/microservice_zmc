package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Store;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
public interface StoreService {
    Store getStoreById(Long id) throws Exception;
}
