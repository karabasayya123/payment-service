package com.microservices.payment_service.FeignClient;

import com.microservices.payment_service.Dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/orders/getOrder/{id}")
    OrderDto getOrderById(@PathVariable Long id);
}

