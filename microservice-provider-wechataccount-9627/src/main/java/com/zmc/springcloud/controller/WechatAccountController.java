package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.service.WechatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class WechatAccountController {
    @Autowired
    private WechatAccountService wechatAccountService;

    @RequestMapping("/wechataccount/{id}")
    public WechatAccount getWechatAccountById(@PathVariable Long id) throws Exception {
        return wechatAccountService.findById(id);
    }

    @RequestMapping("/wechataccount/update/totalbalance")
    public void updateTotalBalance(WechatAccount wechatAccount) throws Exception {
        wechatAccountService.updateTotalBalance(wechatAccount);
    }

    @RequestMapping("/wechataccount/update/isnew")
    public void updateIsNew(WechatAccount wechatAccount)throws Exception{
        wechatAccountService.updateIsNew(wechatAccount);
    }

    @RequestMapping("/wechataccount/update/viptotalbalance")
    public void updateVipPointTotalpointTotalbalance(WechatAccount wechatAccount) throws Exception{
        wechatAccountService.updateVipPointTotalpointTotalbalance(wechatAccount);
    }

}
