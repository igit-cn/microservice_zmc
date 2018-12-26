package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Provider;
import java.util.List;

/**
 * @author xyy
 */
public interface ProviderService {
    /** 采购部 特产供应商 列表*/
    List<Provider> getProviderList() throws Exception;
    /** 根据id获取供应商*/
    Provider getProvider(Long providerId) throws Exception;
}
