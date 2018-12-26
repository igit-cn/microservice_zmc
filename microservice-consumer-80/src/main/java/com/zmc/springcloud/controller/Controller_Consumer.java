package com.zmc.springcloud.controller;

import com.zmc.springcloud.entity.HyAdmin;
import com.zmc.springcloud.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xyy on 2018/11/20.
 */
@RestController
public class Controller_Consumer {
    private static final String REST_URL_PREFIX = "http://localhost:8001";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/api/common/submit")
    public Json login(HyAdmin hyAdmin){
        return restTemplate.postForObject(REST_URL_PREFIX + "/common/submit", hyAdmin, Json.class);
    }

    @RequestMapping(value = "/api/common/project/menu")
    public Json menu(){
        return restTemplate.getForObject(REST_URL_PREFIX + "/common/project/menu", Json.class);
    }
}
