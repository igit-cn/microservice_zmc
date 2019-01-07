package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.WechatAccount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface VipFeignClient {
    @RequestMapping(value = "/vip/318",  method = RequestMethod.POST)
    void setVip318(@RequestParam("wechatAccount") WechatAccount wechatAccount, @RequestParam("money")BigDecimal money);
}
