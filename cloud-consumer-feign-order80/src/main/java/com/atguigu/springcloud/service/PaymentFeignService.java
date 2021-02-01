package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResilt;
import com.atguigu.springcloud.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    @GetMapping(value = "/payment/get/{id}")
    CommonResilt<Payment> getPaymentById(@PathVariable("id") Long id);


    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeout();
}
