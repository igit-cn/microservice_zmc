package com.zmc.springcloud.feignclient.common;

import com.zmc.springcloud.entity.CommonSequence;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-common")
public interface CommonSequenceFeignClient {
      @RequestMapping("/sequence/code")
      String getCode(CommonSequence.SequenceTypeEnum type, Long param);

}
