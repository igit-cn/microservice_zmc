package com.zmc.springcloud.feignclient.common;

import com.zmc.springcloud.entity.HyArea;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-common")
public interface HyAreaFeignClient {
    /**调用common模块提供的接口 获取HyArea
     * @param id 地区id
     * @return HyArea
     * */
    @RequestMapping(value = "/area/{id}")
    HyArea getHyAreaById(@PathVariable("id") Long id);
}
