package com.zmc.springcloud.feignclient.wechataccount;

import com.zmc.springcloud.entity.WeDivideModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-wechataccount")
public interface WeDivideModelFeignClient {
    /**
     * 根据提成模型类型和isValid获取WeDivideModel的列表
     */
    @GetMapping("/wedividemodel/list")
    List<WeDivideModel> getWeDivideModelListByModelTypeAndIsValid(String modelType, Boolean isValid);
}
