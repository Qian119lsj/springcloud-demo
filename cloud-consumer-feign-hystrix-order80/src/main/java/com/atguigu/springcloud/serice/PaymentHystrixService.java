package com.atguigu.springcloud.serice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-SERVICE", fallback = PaymentHystrixServiceImpl.class)
public interface PaymentHystrixService {
    @GetMapping("payment/hystrix/ok/{id}")
    String paymentInfo_ok(@PathVariable("id") Integer id);
    @GetMapping("payment/hystrix/timeout/{id}")
    String paymentTimeout(@PathVariable("id") Integer id);

}
