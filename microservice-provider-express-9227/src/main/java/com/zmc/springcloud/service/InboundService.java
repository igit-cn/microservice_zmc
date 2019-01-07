package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Inbound;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface InboundService {
    /** 需要注意第二个参数*/
    List<Inbound> getInboundListBySpecificationId(Long specificationId, Integer quantity) throws Exception;

    List<Inbound> getListBySpecificationIdAndDepotCode(Long specificationId, String depotCode) throws Exception;
}
