package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.WechatAccount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface VipFeignClient {
    @RequestMapping("/vip/318")
    void setVip318(WechatAccount wechatAccount, BigDecimal money);
}
