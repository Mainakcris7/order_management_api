package com.practice.order_management.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class IndexController {
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> indexRoute() {
        // Using LinkedHashMap to maintain the order of the keys, based on their
        // insertion order
        // To display the 'message' first, it is done
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("message", "Welcome to 'Order Management' API");
        responseMap.put("customers", Map.of("url", "http://localhost:8080/customers"));
        responseMap.put("orders", Map.of("url", "http://localhost:8080/orders"));
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

}
