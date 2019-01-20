package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Viplevel;
import com.zmc.springcloud.mapper.ViplevelMapper;
import com.zmc.springcloud.service.ViplevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Service
public class ViplevelServiceImpl implements ViplevelService{
    @Autowired
    private ViplevelMapper viplevelMapper;

    @Override
    public List<Viplevel> findAll()throws Exception {
        return viplevelMapper.findAll();
    }

    @Override
    public Viplevel getViplevelBywechataccountId(Long wechatAccountId) {
        return viplevelMapper.getViplevelBywechataccountId(wechatAccountId);
    }
}
