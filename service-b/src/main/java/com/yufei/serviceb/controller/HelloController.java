package com.yufei.serviceb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello/{name}")
    public String hello(@PathVariable("name") String name){
        if ("slow".equals(name)) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if ("fail".equals(name)) throw new RuntimeException("forced failure");
        return "Hello " + name + " from service-b";
    }
}
