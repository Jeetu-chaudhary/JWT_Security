package com.deepjava.jwtlogin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class Welcome {
    @GetMapping("/welcome")
    public ResponseEntity<Map> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome");
        return ResponseEntity.ok(response);
    }
}


