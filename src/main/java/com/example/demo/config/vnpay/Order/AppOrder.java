package com.example.demo.config.vnpay.Order;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class AppOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderInfo; // thông tin sản phẩm
    private int amount; // số tiền
    private String status; // "UNPAID" hoặc "PAID"
    private String vnpTxnRef; // mã giao dịch VNPAY
    private Date createdAt;
    private Date paidAt;

    // Getter, Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderInfo() { return orderInfo; }
    public void setOrderInfo(String orderInfo) { this.orderInfo = orderInfo; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVnpTxnRef() { return vnpTxnRef; }
    public void setVnpTxnRef(String vnpTxnRef) { this.vnpTxnRef = vnpTxnRef; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getPaidAt() { return paidAt; }
    public void setPaidAt(Date paidAt) { this.paidAt = paidAt; }
}