package com.example.demo.config.vnpay.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/app")
public class AppOrderController {
    @Autowired
    private VnpayAppService vnpayAppService;
    @Autowired
    private AppOrderRepository appOrderRepository;

    // Tạo đơn và lấy url thanh toán cho app
    @PostMapping("/order")
    public Map<String, String> createOrder(@RequestBody Map<String, Object> body) {
        int amount = (int) body.get("amount");
        String orderInfo = (String) body.get("orderInfo");

        // Tạo đơn trạng thái UNPAID, sinh txnRef
        AppOrder order = new AppOrder();
        order.setAmount(amount);
        order.setOrderInfo(orderInfo);
        order.setStatus("UNPAID");
        order.setCreatedAt(new Date());
        String txnRef = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        order.setVnpTxnRef(txnRef);
        order = appOrderRepository.save(order);

        // Tạo URL thanh toán
        String baseUrl = "https://duc-spring.ngodat0103.live/demo/pay";
        String paymentUrl = vnpayAppService.createOrder(amount, orderInfo, baseUrl, txnRef);

        Map<String, String> rsp = new HashMap<>();
        rsp.put("paymentUrl", paymentUrl);
        rsp.put("orderId", String.valueOf(order.getId()));
        return rsp;
    }

    // Kiểm tra trạng thái đơn
    @GetMapping("/order/{id}")
    public Map<String, String> getOrderStatus(@PathVariable("id") Long id) {
        Optional<AppOrder> orderOpt = appOrderRepository.findById(id);
        Map<String, String> rsp = new HashMap<>();
        if (orderOpt.isPresent()) {
            rsp.put("status", orderOpt.get().getStatus());
        } else {
            rsp.put("status", "NOT_FOUND");
        }
        return rsp;
    }
}