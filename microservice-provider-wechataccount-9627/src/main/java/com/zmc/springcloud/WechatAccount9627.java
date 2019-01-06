package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by xyy on 2019/1/6.
 *
 * @author xyy
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class WechatAccount9627 {
    public static void main(String[] args) {
        SpringApplication.run(WechatAccount9627.class, args);
    }
}
