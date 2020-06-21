package com.wangpx.service;

import org.springframework.http.ResponseEntity;

public interface RPCService {
    ResponseEntity<String> rpc(String message);
}
