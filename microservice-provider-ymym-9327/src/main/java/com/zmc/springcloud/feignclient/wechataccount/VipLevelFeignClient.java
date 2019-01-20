package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.Viplevel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface VipLevelFeignClient {
    @RequestMapping("/viplevel/wechataccount/{wechataccountid}")
    Viplevel getViplevelBywechataccountId(@PathVariable Long wechataccountid);
}
