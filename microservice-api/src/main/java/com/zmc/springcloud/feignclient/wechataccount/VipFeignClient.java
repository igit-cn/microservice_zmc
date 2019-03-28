package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.Viplevel;
import com.zmc.springcloud.entity.WechatAccount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface VipFeignClient {
    @RequestMapping(value = "/vip/318",  method = RequestMethod.POST)
    void setVip318(@RequestBody WechatAccount wechatAccount, @RequestParam("money") BigDecimal money);

    @RequestMapping("/viplevel/wechataccount/{wechataccountid}")
    Viplevel getViplevelBywechataccountId(@PathVariable Long wechataccountid);
}
