package com.zmc.springcloud.feignclient.supplier;

import com.zmc.springcloud.entity.Provider;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by xyy on 2019/1/4.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-supplier")
public interface ProviderFeignClient {
    @RequestMapping(value = "/provider/{id}")
    Provider getProviderById(@PathVariable("id") Long id);

    @RequestMapping(value = "/provider/list", method = RequestMethod.GET)
    List<Provider> getListProvider(@RequestParam("state") Boolean state, @RequestParam("providerType") Long providerType, @RequestParam("providerName") String providerName, @RequestParam("contactorName") String contactorName);
}