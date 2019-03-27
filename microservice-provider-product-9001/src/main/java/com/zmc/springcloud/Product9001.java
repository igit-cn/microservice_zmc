package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyy on 2018/11/23.
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.zmc.springcloud.feignclient"})
public class Product9001 {
    public static void main(String[] args) {
        SpringApplication.run(Product9001.class, args);
    }
}