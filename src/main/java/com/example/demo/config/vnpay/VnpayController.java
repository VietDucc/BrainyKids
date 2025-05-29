package com.example.demo.config.vnpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
public class VnpayController {

    @Autowired
    private VnpayService vnpayService;

    @PostMapping("/create_payment_url")
    public ResponseEntity<?> createPaymentUrl(@RequestBody Map<String, String> data, HttpServletRequest request) {
        String amountStr = data.get("amount");
        String clerkUserId = data.get("clerkUserId");
        String orderType = data.getOrDefault("orderType", "other"); // mặc định là 'other'
        if (amountStr == null || clerkUserId == null) {
            return ResponseEntity.badRequest().body("Missing amount or clerkUserId");
        }
        long amount = Long.parseLong(amountStr);

        String ipAddr = getClientIp(request);

        String orderInfo = "clerkUserId:" + clerkUserId;

        String paymentUrl = vnpayService.createPaymentUrl(amount, orderInfo, orderType, ipAddr);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return ResponseEntity.ok(response);
    }
    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Nếu có nhiều IP (proxy), lấy IP đầu tiên
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}