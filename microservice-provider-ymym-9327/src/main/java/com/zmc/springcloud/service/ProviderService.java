package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Provider;

/**
 * Created by xyy on 2018/12/15.
 *
 * @author xyy
 */
public interface ProviderService {
    /** 获取供应商*/
    Provider find(Long providerId)throws Exception;
}
