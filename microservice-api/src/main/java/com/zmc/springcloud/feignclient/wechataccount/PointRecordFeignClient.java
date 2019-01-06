package com.zmc.springcloud.feignclient.wechataccount;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface PointRecordFeignClient {
    @RequestMapping("/pointrecord/modify")
    void changeUserPoint(Long orderWechatId, Integer changevalue, String reason);
}
