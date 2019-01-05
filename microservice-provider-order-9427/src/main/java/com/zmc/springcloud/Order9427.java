package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyy on 2018/12/5.
 *
 * @author xyy
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Order9427 {
    public static void main(String[] args) {
        SpringApplication.run(Order9427.class, args);
    }

}
