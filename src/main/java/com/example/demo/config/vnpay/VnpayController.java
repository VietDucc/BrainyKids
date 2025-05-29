package com.example.demo.config.vnpay;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class VnpayController {
    @Autowired
    private VnpayService vnPayService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String home(){
        return "index";
    }

    @PostMapping("/submitOrder")
    @ResponseBody
    public Map<String, String> submidOrder(@RequestParam("amount") int orderTotal,
                                           @RequestParam("orderInfo") String orderInfo,
                                           HttpServletRequest request) {
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String baseUrl = "https://duc-spring.ngodat0103.live/demo";

        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", vnpayUrl); // Trả về đúng field bạn mong muốn

        return response;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
    @GetMapping("/IPN")
    public ResponseEntity<Map<String, String>> receiveIPN(@RequestParam Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String orderInfo = params.get("vnp_OrderInfo"); // đây là clerkUserId bạn gửi
        Map<String, String> rsp = new HashMap<>();

        // Giả sử bạn đã check checksum, dữ liệu hợp lệ ở bước này

        if ("00".equals(responseCode)) {
            // Tìm user theo clerkUserId
            Optional<User> userOpt = userRepository.findByClerkUserId(orderInfo);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setActive(true);  // cập nhật active = true
                userRepository.save(user);

                System.out.println("User with clerkUserId: " + orderInfo + " has been activated.");

                rsp.put("RspCode", "00");
                rsp.put("Message", "Confirm Success");
                return ResponseEntity.ok(rsp);
            } else {
                rsp.put("RspCode", "01");
                rsp.put("Message", "User not found");
                return ResponseEntity.ok(rsp);
            }
        } else {
            rsp.put("RspCode", "02");
            rsp.put("Message", "Payment failed");
            return ResponseEntity.ok(rsp);
        }
    }

}
