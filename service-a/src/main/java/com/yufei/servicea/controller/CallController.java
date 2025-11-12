package com.yufei.servicea.controller;

import com.yufei.servicea.service.CallService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {

    @Resource
    private CallService callService;

    @GetMapping("/call/{name}")
    public String call(@PathVariable("name") String name) {
        return callService.call(name);
    }
}
