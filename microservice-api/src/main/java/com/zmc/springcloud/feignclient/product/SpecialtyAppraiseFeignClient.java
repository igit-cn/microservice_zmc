package com.zmc.springcloud.feignclient.product;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * Created by xyy on 2019/3/26.
 *
 * @author xyy
 */
@FeignClient(name = "microservicecloud-product")
public interface SpecialtyAppraiseFeignClient {
    /**
     * 根据specialtyid获取isShow isValid的SpecialtyAppraise
     */
    @GetMapping("/specialty/appraise")
    HashMap<String, Object> getSpecialtyAppraiseBySpecialtyId(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("specialtyId") Long specialtyId);
}
