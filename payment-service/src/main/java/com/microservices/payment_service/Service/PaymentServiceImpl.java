package com.microservices.payment_service.Service;

import com.microservices.payment_service.Dto.PaymentDto;
import com.microservices.payment_service.Entity.PaymentEntity;
import com.microservices.payment_service.FeignClient.OrderClient;
import com.microservices.payment_service.Repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONObject;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private OrderClient orderClient;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Override
    public PaymentDto createPayment(Long orderId) {

        // Get order details
        var order = orderClient.getOrderById(orderId);

        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();
            options.put("amount", order.getTotalPrice() * 100); // paise
            options.put("currency", "INR");
            options.put("receipt", "order_" + orderId);

            Order razorOrder = razorpay.orders.create(options);

            PaymentEntity entity = new PaymentEntity();
            entity.setOrderId(orderId);
            entity.setUserId(order.getUserId());
            entity.setAmount(order.getTotalPrice());
            entity.setRazorpayOrderId(razorOrder.get("id"));
            entity.setStatus("CREATED");

            PaymentEntity saved = repo.save(entity);

            PaymentDto dto = new PaymentDto();
            dto.setId(saved.getId());
            dto.setOrderId(saved.getOrderId());
            dto.setUserId(saved.getUserId());
            dto.setAmount(saved.getAmount());
            dto.setRazorpayOrderId(saved.getRazorpayOrderId());
            dto.setStatus(saved.getStatus());

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Payment creation failed");
        }
    }

    @Override
    public PaymentDto updatePaymentStatus(PaymentDto dto) {

        PaymentEntity entity = repo.findByRazorpayOrderId(dto.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        entity.setRazorpayPaymentId(dto.getRazorpayPaymentId());
        entity.setStatus(dto.getStatus());

        repo.save(entity);
        return dto;
    }

}

