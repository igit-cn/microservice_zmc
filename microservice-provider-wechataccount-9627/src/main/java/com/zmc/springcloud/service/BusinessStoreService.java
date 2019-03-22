package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessStore;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
public interface BusinessStoreService {
    /** 根据id获取BusinessStore*/
    BusinessStore getBusinessStoreById(Long id) throws Exception;
}
