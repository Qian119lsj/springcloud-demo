package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResilt;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResilt create(@RequestBody Payment payment) {
        int i = paymentService.create(payment);
        log.info("插入结果: = " + i);
        if (i > 0) {
            return new CommonResilt(200, "插入成功,serverPort:"+serverPort, i);
        } else {
            return new CommonResilt(444, "插入失败");
        }

    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResilt getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        if (paymentById != null) {
            return new CommonResilt(200, "查询成功,serverPort:"+serverPort, paymentById);
        } else {
            return new CommonResilt(444, "查询失败,id={}", id);
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
