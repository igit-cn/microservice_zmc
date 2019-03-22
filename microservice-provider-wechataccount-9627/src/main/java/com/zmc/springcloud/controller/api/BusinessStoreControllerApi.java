package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.BusinessStore;
import com.zmc.springcloud.service.BusinessStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyy on 2019/3/22.
 *
 * @author xyy
 */
@RestController
public class BusinessStoreControllerApi {
    @Autowired
    private BusinessStoreService businessStoreService;

    @GetMapping("/businessstore/{id}")
    public BusinessStore getBusinessStoreById(@PathVariable("id")Long id) throws Exception{
        return businessStoreService.getBusinessStoreById(id);
    }
}
