package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResilt;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("**service: " + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return discoveryClient;
    }


    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }


    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String zipkin() {
        return "welcome to zipkin!";
    }

}
