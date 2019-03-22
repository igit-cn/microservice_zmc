package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.mapper.WechatAccountMapper;
import com.zmc.springcloud.service.WechatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@Service
public class WechatAccountServiceImpl implements WechatAccountService{
    @Autowired
    private WechatAccountMapper wechatAccountMapper;

    @Override
    public WechatAccount findById(Long wechatId)throws Exception {
        return wechatAccountMapper.findById(wechatId);
    }

    @Override
    public void updateTotalBalance(WechatAccount wechatAccount)throws Exception {
        wechatAccountMapper.updateTotalBalance(wechatAccount);
    }

    @Override
    public void updateIsNew(WechatAccount wechatAccount)throws Exception {
        wechatAccountMapper.upIsNew(wechatAccount);
    }

    @Override
    public void updateVip(WechatAccount wechatAccount)throws Exception {
        wechatAccountMapper.updateVip(wechatAccount);
    }

    @Override
    public void updateVipPointTotalpointTotalbalance(WechatAccount wechatAccount) throws Exception {
        wechatAccountMapper.updateVipPointTotalpointTotalbalance(wechatAccount);
    }

    @Override
    public List<WechatAccount> getWechatAccountListByOpenId(String openId) throws Exception {
        return wechatAccountMapper.findWechatAccountListByOpenId(openId);
    }

    @Override
    public void createWechatAccount(WechatAccount wechatAccount) {
        wechatAccountMapper.insert(wechatAccount);
    }
}
