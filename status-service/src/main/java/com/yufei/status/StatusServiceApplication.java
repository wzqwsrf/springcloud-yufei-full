package com.yufei.status;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
//@MapperScan("com.yufei.status.mapper")
public class StatusServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatusServiceApplication.class, args);
    }
}