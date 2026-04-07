package com.example.rate_limitter.rate_limitter_poc.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testEndpoint(){
        return "Hello, World!";
    }

}
