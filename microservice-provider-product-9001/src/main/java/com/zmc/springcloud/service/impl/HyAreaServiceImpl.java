package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.HyArea;
import com.zmc.springcloud.mapper.HyAreaMapper;
import com.zmc.springcloud.service.HyAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyy on 2018/12/8.
 *
 * @author xyy
 */
@Service
public class HyAreaServiceImpl implements HyAreaService{
    @Autowired
    private HyAreaMapper hyAreaMapper;

    /** 根据id获取HyArea*/
    @Override
    public HyArea getHyAreaById(Long areaId) throws Exception {
        return hyAreaMapper.findById(areaId);
    }
}
