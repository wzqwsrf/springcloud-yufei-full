package com.yufei.servicea.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-b")
public interface ServiceBClient {
    @GetMapping("/api/hello/{name}")
    String hello(@PathVariable("name") String name);
}
