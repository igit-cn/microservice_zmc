package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Ship;
import com.zmc.springcloud.mapper.ShipMapper;
import com.zmc.springcloud.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@Service
public class ShipServiceImpl implements ShipService {
    @Autowired
    private ShipMapper shipMapper;

    @Override
    public Ship getShipById(Long shipId) throws Exception {
        return shipMapper.findById(shipId);
    }
}
