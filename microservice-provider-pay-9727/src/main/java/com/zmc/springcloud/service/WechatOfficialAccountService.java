package com.zmc.springcloud.service;

import com.zmc.springcloud.util.WechatOfficialAccount;

import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
public interface WechatOfficialAccountService {
    List<WechatOfficialAccount> findList()throws Exception;
}
