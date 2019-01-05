package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyy on 2018/12/25.
 *
 * @author xyy
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Express9227 {
    public static void main(String[] args) {
        SpringApplication.run(Express9227.class, args);
    }

}