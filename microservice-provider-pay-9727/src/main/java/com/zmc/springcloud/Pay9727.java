package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyy on 2019/1/20.
 *
 * @author xyy
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Pay9727 {
    public static void main(String[] args) {
        SpringApplication.run(Pay9727.class, args);
    }

}