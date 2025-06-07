package com.example.demo.config.vnpay.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class VnpayAppReturnController {
    @Autowired
    private AppOrderRepository appOrderRepository;

    @GetMapping("/vnpay-payment-app")
    public String handleVnpayReturn(HttpServletRequest request, Model model) {
        String vnpTxnRef = request.getParameter("vnp_TxnRef");
        String responseCode = request.getParameter("vnp_ResponseCode");
        String paymentTime = request.getParameter("vnp_PayDate");
        String amount = request.getParameter("vnp_Amount");

        Optional<AppOrder> orderOpt = appOrderRepository.findByVnpTxnRef(vnpTxnRef);

        boolean isSuccess = "00".equals(responseCode) && orderOpt.isPresent() && "PAID".equals(orderOpt.get().getStatus());

        model.addAttribute("orderId", vnpTxnRef);
        model.addAttribute("amount", amount);
        model.addAttribute("paymentTime", paymentTime);

        if (isSuccess) {
            return "app_payment_success"; // trả về file HTML này (resources/templates/app_payment_success.html)
        } else {
            return "app_payment_fail"; // trả về file HTML này (resources/templates/app_payment_fail.html)
        }
    }
}