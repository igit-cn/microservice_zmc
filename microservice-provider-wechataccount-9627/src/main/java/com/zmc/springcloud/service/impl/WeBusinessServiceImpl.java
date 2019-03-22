package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.WeBusiness;
import com.zmc.springcloud.mapper.WeBusinessMapper;
import com.zmc.springcloud.service.WeBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@Service
public class WeBusinessServiceImpl implements WeBusinessService{
    @Autowired
    private WeBusinessMapper weBuinessMapper;

    @Override
    public WeBusiness getWeBusinessById(Long id) throws Exception {
        return weBuinessMapper.selectWeBusinessById(id);
    }

    @Override
    public List<WeBusiness> getWeBusinessListByOpenId(String openId) throws Exception {
        return weBuinessMapper.selectWeBusinessListByOpenId(openId);
    }
}
