package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.Provider;
import com.zmc.springcloud.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xyy on 2019/1/10.
 *
 * @author xyy
 */
@RestController
public class ProviderControllerApi {
    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/provider/{id}")
    public Provider getProviderById(@PathVariable("id") Long id) {
        return providerService.getProviderById(id);
    }

    @RequestMapping(value = "/provider/list")
    public List<Provider> getListProvider(Boolean state, Long providerType, String providerName, String contactorName) {
        return providerService.findListProvider(state, providerType, providerName, contactorName);
    }
}
