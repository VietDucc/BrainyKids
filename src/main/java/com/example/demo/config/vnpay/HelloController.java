package com.example.demo.config.vnpay;


import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class HelloController {
    @PostMapping("/test")
    public String hello(@RequestBody Map<String, String> data, HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}