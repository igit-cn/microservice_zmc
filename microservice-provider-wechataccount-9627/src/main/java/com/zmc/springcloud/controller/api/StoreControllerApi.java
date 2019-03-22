package com.zmc.springcloud.controller.api;

import com.zmc.springcloud.entity.Store;
import com.zmc.springcloud.service.StoreService;
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
public class StoreControllerApi {
    @Autowired
    private StoreService storeService;

    @GetMapping("/store/{id}")
    public Store getStoreById(@PathVariable("id")Long id) throws Exception{
        return storeService.getStoreById(id);
    }
}
