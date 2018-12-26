package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.BusinessOrderOutbound;

import java.util.List;

/**
 * Created by xyy on 2018/12/6.
 *
 * @author xyy
 */
public interface BusinessOrderOutboundService {
    List<BusinessOrderOutbound> getListByBusinessOrderItemId(Long id) throws Exception;
}
