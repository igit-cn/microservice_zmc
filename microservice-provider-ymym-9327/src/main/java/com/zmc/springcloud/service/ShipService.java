package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Ship;

import java.util.List;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface ShipService {
    Ship getShipById(Long shipId) throws Exception;
    /** 根据orderId获取Ship的list*/
    List<Ship> getShipListByOrderId(Long orderId) throws Exception;
}
