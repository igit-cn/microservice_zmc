package com.zmc.springcloud.feignclient.common;

import com.zmc.springcloud.entity.CommonSequence;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-common")
public interface CommonSequenceFeignClient {
      @RequestMapping(value = "/sequence/code", method = RequestMethod.GET)
      String getCode(@RequestParam("type") CommonSequence.SequenceTypeEnum type, @RequestParam("param") Long param);
}
