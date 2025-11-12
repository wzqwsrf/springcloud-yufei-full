package com.yufei.servicea.service;

import com.yufei.servicea.feign.ServiceBClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wangzhenqing
 * @date 2025/11/12 11:00
 * @description
 */
@Service
public class CallService {

    @Resource
    private ServiceBClient client;

    @CircuitBreaker(name = "service-b", fallbackMethod = "fallbackHello")
    public String call(String name) {
        return client.hello(name); // Feign 调用
//        throw new RuntimeException("手动抛出异常，模拟调用失败");
    }

    public String fallbackHello(String name, Throwable t) {
        System.out.println("Fallback triggered: " + t.getMessage());
        return "fallback: " + name;
    }
}

