package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by xyy on 2019/1/3.
 *
 * @author xyy
 */

@SpringBootApplication
//本服务启动后会自动注册进eureka服务中
@EnableEurekaClient
@EnableDiscoveryClient
public class Common9127 {
    public static void main(String[] args) {
        SpringApplication.run(Common9127.class, args);
    }

}