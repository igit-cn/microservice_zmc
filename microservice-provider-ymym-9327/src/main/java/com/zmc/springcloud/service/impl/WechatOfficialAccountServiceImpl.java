package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.mapper.WechatOfficialAccountMapper;
import com.zmc.springcloud.service.WechatOfficialAccountService;
import com.zmc.springcloud.util.WechatOfficialAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Service
public class WechatOfficialAccountServiceImpl implements WechatOfficialAccountService {
    @Autowired
    private WechatOfficialAccountMapper wechatOfficialAccountMapper;
    @Override
    public List<WechatOfficialAccount> findList()throws Exception {
        return wechatOfficialAccountMapper.findList();
    }
}
