package com.zmc.springcloud.feignclient.wechataccount;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface PointRecordFeignClient {
    @RequestMapping(value = "/pointrecord/modify", method = RequestMethod.POST)
    void changeUserPoint(@RequestParam("orderWechatId") Long orderWechatId, @RequestParam("changevalue")Integer changevalue, @RequestParam("reason")String reason);
}
