package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyy on 2018/12/13.
 *
 * @author xyy
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Ymym9327 {
    public static void main(String[] args) {
        SpringApplication.run(Ymym9327.class, args);
    }
}