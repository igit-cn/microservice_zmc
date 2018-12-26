package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.Vip;
import com.zmc.springcloud.entity.WechatAccount;

import java.math.BigDecimal;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
public interface VipService {
    void setVip318(WechatAccount wechatAccount, BigDecimal money)throws Exception;

    Vip getVipByWechatAccount(Long id) throws Exception;

    void save(Vip newVip) throws Exception;

    void updateVipLevel(Vip vip) throws Exception;
}
