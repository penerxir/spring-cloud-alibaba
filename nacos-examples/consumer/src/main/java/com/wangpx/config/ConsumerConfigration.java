package com.wangpx.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class ConsumerConfigration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
