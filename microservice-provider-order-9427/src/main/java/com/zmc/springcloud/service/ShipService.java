package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Ship;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
public interface ShipService {
    Ship getShipById(Long shipId) throws Exception;
}
