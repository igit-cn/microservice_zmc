package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.entity.Vip;
import com.zmc.springcloud.entity.Viplevel;
import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.mapper.VipMapper;
import com.zmc.springcloud.service.VipService;
import com.zmc.springcloud.service.ViplevelService;
import com.zmc.springcloud.service.WechatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/12/16.
 *
 * @author xyy
 */
@Service
public class VipServiceImpl implements VipService{
    @Autowired
    private VipMapper vipMapper;

    @Autowired
    private ViplevelService viplevelService;

    @Autowired
    private WechatAccountService wechatAccountService;

    @Override
    public void setVip318(WechatAccount wechatAccount, BigDecimal money) throws Exception{
        if(wechatAccount == null){
            return;
        }
        BigDecimal vipMoney = BigDecimal.valueOf(318);
        if(money.compareTo(vipMoney)>=0 && wechatAccount.getIsVip().equals(false)) {
            //获取会员等级
            List<Viplevel> viplevels = viplevelService.findAll();
            Viplevel viplevel = viplevels.get(0);
            //设置账号为VIP
            wechatAccount.setIsVip(true);
            Vip vip = vipMapper.getVipByWechatAccount(wechatAccount.getId());
            if(vip == null){
                // 如果没有会员,则新建会员记录
                Vip newVip = new Vip();
                newVip.setViplevelId(viplevel.getId());
                newVip.setWechatAccount(wechatAccount.getId());
                newVip.setCreateTime(new Date());
                vipMapper.insert(newVip);
            }else{
                vip.setViplevelId(viplevel.getId());
                vipMapper.updateVipLevel(vip);
            }
            // 保存微信用户信息
            wechatAccountService.updateVip(wechatAccount);
        }
    }

    @Override
    public Vip getVipByWechatAccount(Long wechatAccountId) throws Exception {
        return vipMapper.getVipByWechatAccount(wechatAccountId);
    }

    @Override
    public void save(Vip newVip) throws Exception {
        vipMapper.insert(newVip);
    }

    @Override
    public void updateVipLevel(Vip vip) throws Exception {
        vipMapper.updateVipLevel(vip);
    }

}
