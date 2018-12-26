package com.zmc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by xyy on 2018/11/20.
 */
@SpringBootApplication
@EnableEurekaClient
public class Consumer80_App {
    public static void main(String[] args) {
        SpringApplication.run(Consumer80_App.class, args);
    }
}
