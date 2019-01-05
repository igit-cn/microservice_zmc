package com.zmc.springcloud.feignclient.supplier;

import com.zmc.springcloud.entity.Provider;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-supplier")
public interface ProviderFeignClient {
    @RequestMapping(value = "/provider/{id}")
    Provider getProviderById(@PathVariable Long id);

    @RequestMapping(value = "/provider/list")
    List<Provider> getListProvider(Boolean state, Long providerType, String providerName, String contactorName);
}