package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.WeBusiness;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by xyy on 2019/3/21.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface WeBusinessFeignClient {
    /** 根据id获取WeBusiness*/
    @GetMapping("/wechataccount/webusiness/{id}")
    WeBusiness getWeBusinessById(Long id);

    /** 根据openId获取WeBusiness的List*/
    @GetMapping("/wechataccount/webusiness/{openId}")
    List<WeBusiness> getWeBusinessListByOpenId(@PathVariable("openId")String openId);
}
