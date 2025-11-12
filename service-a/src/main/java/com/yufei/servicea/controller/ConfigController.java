package com.yufei.servicea.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Value("${sample.greeting}")
    private String message;

    @GetMapping("/config")
    public String getMessage(){
        return message;
    }
}
