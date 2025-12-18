package com.microservices.payment_service.Repository;

import com.microservices.payment_service.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByRazorpayOrderId(String razorpayOrderId);
}

