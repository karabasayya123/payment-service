package com.microservices.payment_service.Controller;

import com.microservices.payment_service.Dto.PaymentDto;
import com.microservices.payment_service.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/create/{orderId}")
    public PaymentDto createPayment(@PathVariable Long orderId) {
        return service.createPayment(orderId);
    }

    @PostMapping("/update")
    public PaymentDto updatePayment(@RequestBody PaymentDto dto) {
        return service.updatePaymentStatus(dto);
    }
}
