package com.example.demo.config.vnpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnpayService {

    @Autowired
    private VnpayConfig config;

    public String createPaymentUrl(long amount, String orderInfo, String orderType, String ipAddr) {
        try {
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "pay");
            vnp_Params.put("vnp_TmnCode", safe(config.getTmnCode()));
            vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis()));
            vnp_Params.put("vnp_OrderInfo", safe(orderInfo));
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", safe(config.getReturnUrl()));
            vnp_Params.put("vnp_IpAddr", safe(ipAddr));
            vnp_Params.put("vnp_OrderType", safe(orderType));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            String vnp_CreateDate = formatter.format(new Date());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            Calendar expire = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            expire.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(expire.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Xóa các key có giá trị null/rỗng
            vnp_Params.entrySet().removeIf(e -> e.getValue() == null || e.getValue().isEmpty());

            // Sort keys
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            // Build hashData (KHÔNG encode)
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (int i = 0; i < fieldNames.size(); i++) {
                String fieldName = fieldNames.get(i);
                String fieldValue = vnp_Params.get(fieldName);

                hashData.append(fieldName).append("=").append(fieldValue);

                // Encode khi build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()))
                        .append("=")
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                if (i < fieldNames.size() - 1) {
                    hashData.append("&");
                    query.append("&");
                }
            }

            String vnp_SecureHash = hmacSHA512(safe(config.getHashSecret()), hashData.toString());
            String paymentUrl = safe(config.getPayUrl()) + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;

            // Log debug
            System.out.println("HashData: " + hashData);
            System.out.println("Secret: " + safe(config.getHashSecret()));
            System.out.println("Hash: " + vnp_SecureHash);
            System.out.println("PaymentURL: " + paymentUrl);

            return paymentUrl;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Helper: trả về "" nếu null, trim nếu có giá trị
    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private String hmacSHA512(String key, String data) throws Exception {
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
        hmac512.init(secretKey);

        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] result = hmac512.doFinal(dataBytes);

        StringBuilder sb = new StringBuilder(2 * result.length);
        for (byte b : result) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}