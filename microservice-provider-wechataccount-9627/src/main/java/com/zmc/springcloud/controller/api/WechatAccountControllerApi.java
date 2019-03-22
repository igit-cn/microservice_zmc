package com.zmc.springcloud.controller.api;

import com.netflix.discovery.converters.Auto;
import com.zmc.springcloud.entity.WeBusiness;
import com.zmc.springcloud.entity.WechatAccount;
import com.zmc.springcloud.service.WeBusinessService;
import com.zmc.springcloud.service.WechatAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@RestController
public class WechatAccountControllerApi {
    @Autowired
    private WechatAccountService wechatAccountService;

    @RequestMapping("/wechataccount/{id}")
    public WechatAccount getWechatAccountById(@PathVariable("id") Long id) throws Exception {
        return wechatAccountService.findById(id);
    }

    @RequestMapping("/wechataccount/update/totalbalance")
    public void updateTotalBalance(@RequestBody WechatAccount wechatAccount) throws Exception {
        wechatAccountService.updateTotalBalance(wechatAccount);
    }

    @RequestMapping("/wechataccount/update/isnew")
    public void updateIsNew(@RequestBody WechatAccount wechatAccount)throws Exception{
        wechatAccountService.updateIsNew(wechatAccount);
    }

    @RequestMapping("/wechataccount/update/viptotalbalance")
    public void updateVipPointTotalpointTotalbalance(@RequestBody WechatAccount wechatAccount) throws Exception{
        wechatAccountService.updateVipPointTotalpointTotalbalance(wechatAccount);
    }

    /** 根据openId获取WechatAccount的List*/
    @GetMapping("/wechataccount/{openId}")
    List<WechatAccount> getWechatAccountListByOpenId(@PathVariable("openId")String openId) throws Exception{
        return wechatAccountService.getWechatAccountListByOpenId(openId);
    }

    /** 创建新的wechatAccount*/
    @PostMapping("/wechataccount/create")
    void createWechatAccount(@RequestBody WechatAccount wechatAccount) throws Exception{
        wechatAccountService.createWechatAccount(wechatAccount);
    }
}
