package com.example.demo.config.vnpay.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AppOrderRepository extends JpaRepository<AppOrder, Long> {
    Optional<AppOrder> findByVnpTxnRef(String vnpTxnRef);
}