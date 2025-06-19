package com.example.demo.config.vnpay.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/app")
public class VnpayAppIpnController {
    @Autowired
    private AppOrderRepository appOrderRepository;

    @GetMapping("/ipn")
    public ResponseEntity<Map<String, String>> receiveIPN(@RequestParam Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String vnpTxnRef = params.get("vnp_TxnRef");
        Map<String, String> rsp = new HashMap<>();

        // TODO: Check checksum tại đây nếu cần

        if ("00".equals(responseCode)) {
            Optional<AppOrder> orderOpt = appOrderRepository.findByVnpTxnRef(vnpTxnRef);
            if (orderOpt.isPresent()) {
                AppOrder order = orderOpt.get();
                order.setStatus("PAID");
                order.setPaidAt(new Date());
                appOrderRepository.save(order);

                rsp.put("RspCode", "00");
                rsp.put("Message", "Confirm Success");
                return ResponseEntity.ok(rsp);
            } else {
                rsp.put("RspCode", "01");
                rsp.put("Message", "Order not found");
                return ResponseEntity.ok(rsp);
            }
        } else {
            rsp.put("RspCode", "02");
            rsp.put("Message", "Payment failed");
            return ResponseEntity.ok(rsp);
        }
    }
}