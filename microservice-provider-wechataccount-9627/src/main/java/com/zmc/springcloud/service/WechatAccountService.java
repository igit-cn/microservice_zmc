package com.zmc.springcloud.service;

import com.zmc.springcloud.entity.WechatAccount;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
public interface WechatAccountService {
    /** 根据Id获取WechatAccount*/
    WechatAccount findById(Long wechatId)throws Exception;
    /** 更新账户余额*/
    void updateTotalBalance(WechatAccount wechatAccount)throws Exception;
    /** 将用户由新用户修改为老用户*/
    void updateIsNew(WechatAccount wechatAccount)throws Exception;
    /** 更新Vip等级*/
    void updateVip(WechatAccount wechatAccount)throws Exception;
    /** 更新vip等级 可用积分 总积分 用户余额*/
    void updateVipPointTotalpointTotalbalance(WechatAccount wechatAccount) throws Exception;
}
