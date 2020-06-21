package com.wangpx.controller;


import com.wangpx.service.RPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ConsumerController {


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RPCService rpcService;


    @Autowired
    private RestTemplate restTemplate;


    //@Value("${}")

    /**
     * 根据服务id获取服务实例列表
     * @param serviceName
     * @return
     */
    @GetMapping("/discovery/{serviceName}")
    @CrossOrigin
    public ResponseEntity<List<String>> discovery (@PathVariable("serviceName") String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

        if (instances == null || instances.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<String> services = new ArrayList<>(instances.size());
        instances.forEach(instance ->{
            services.add(instance.getHost()+":"+instance.getPort());
            System.out.println(instance.getHost()+":"+instance.getPort());
        });
        return ResponseEntity.ok(services);
    }

    @GetMapping("/rpc/{message}")
    public ResponseEntity<String> rpc (@PathVariable("message") String message) {
        return rpcService.rpc(message);
    }

    @GetMapping("/rpc2/{message}")
    public ResponseEntity<String> rpc2(@PathVariable("message") String message) {
        List<ServiceInstance> instances = discoveryClient.getInstances("provider-service");
        System.out.println(instances);

        if (instances == null || instances.isEmpty()) {
            return ResponseEntity.badRequest().body("当前服务没有服务提供者");
        }

        ServiceInstance loadbalance = loadBalance(instances);
        System.out.println(loadbalance);
        String instance = loadbalance.getHost()+":"+loadbalance.getPort();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                String.format("http://%s/echo/{message}", instance),
                String.class, message
        );
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(String.format("远程调用成功，结果为%s",forEntity.getBody()));
        }
        return ResponseEntity.badRequest().body("远程调用失败");

    }

    /**
     * 从一个服务实例列表中获取一个服务实例
     * @param instances
     * @return
     */
    private ServiceInstance loadBalance(List<ServiceInstance> instances) {

        Random random = new Random(System.currentTimeMillis());

        return instances.get(random.nextInt(instances.size()));
    }
}
