package com.wangpx.service.impl;


import com.wangpx.service.RPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RPCServiceImpl implements RPCService {

    private static final String HOST="localhost";
    private static final String PORT="8081";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> rpc(String message) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://" + HOST + ":" + PORT + "/echo/{message}"
                , String.class, message);

        if (forEntity.getStatusCode()== HttpStatus.OK) {
            return ResponseEntity.ok(String.format("远程调用成功，结果为%s",forEntity.getBody()));
        }
        return ResponseEntity.badRequest().body("远程调用失败");
    }
}
