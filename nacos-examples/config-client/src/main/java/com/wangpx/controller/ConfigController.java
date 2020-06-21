package com.wangpx.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RefreshScope 代表配置文件是可以被刷新的
 */
@RestController
@RefreshScope
public class ConfigController {



    @Value("${user.age}")
    private Integer age;
    @Value("${user.name}")
    private String name;

    @GetMapping("/config/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok(String.format("从配置中心获取的信息为:age: %S",age));
    }
}
