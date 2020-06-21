package com.wangpx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {


    @Value("${server.port}")
    private Integer port;


    @GetMapping("/echo/{message}")
    public ResponseEntity<String> echo(@PathVariable("message") String message) {
        return ResponseEntity.ok(String.format("hello,%s,我是服务提供者:%s",message,port));
    }
}
