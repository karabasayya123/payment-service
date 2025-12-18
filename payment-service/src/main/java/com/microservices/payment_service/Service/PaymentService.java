package com.microservices.payment_service.Service;

import com.microservices.payment_service.Dto.PaymentDto;

public interface PaymentService {
    PaymentDto createPayment(Long orderId);

    PaymentDto updatePaymentStatus(PaymentDto dto);
}
