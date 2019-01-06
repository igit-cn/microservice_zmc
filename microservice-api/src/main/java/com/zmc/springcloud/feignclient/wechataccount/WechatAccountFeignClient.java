package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.WechatAccount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface WechatAccountFeignClient {

    @RequestMapping("/wechataccount/{id}")
    WechatAccount getWechatAccountById(@PathVariable Long id);

    @RequestMapping("/wechataccount/update/totalbalance")
    void updateTotalBalance(WechatAccount wechatAccount);

    @RequestMapping("/wechataccount/update/viptotalbalance")
    void updateVipPointTotalpointTotalbalance(WechatAccount wechatAccount);

    @RequestMapping("/wechataccount/update/isnew")
    void updateIsNew(WechatAccount wechatAccount);
}
