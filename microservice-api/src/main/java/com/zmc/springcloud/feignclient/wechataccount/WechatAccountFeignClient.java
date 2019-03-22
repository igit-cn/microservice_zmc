package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.WechatAccount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface WechatAccountFeignClient {

    @GetMapping("/wechataccount/{id}")
    WechatAccount getWechatAccountById(@PathVariable("id") Long id);

    @RequestMapping("/wechataccount/update/totalbalance")
    void updateTotalBalance(@RequestBody WechatAccount wechatAccount);

    @RequestMapping("/wechataccount/update/viptotalbalance")
    void updateVipPointTotalpointTotalbalance(@RequestBody WechatAccount wechatAccount);

    @RequestMapping("/wechataccount/update/isnew")
    void updateIsNew(@RequestBody WechatAccount wechatAccount);

    /** 根据openId获取WechatAccount的List*/
    @GetMapping("/wechataccount/{openId}")
    List<WechatAccount> getWechatAccountListByOpenId(@PathVariable("openId")String openId);

    /** 创建新的wechatAccount*/
    @PostMapping("/wechataccount/create")
    void createWechatAccount(@RequestBody WechatAccount wechatAccount);
}
