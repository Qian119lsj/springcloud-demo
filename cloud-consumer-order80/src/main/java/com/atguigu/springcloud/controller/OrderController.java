package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResilt;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

//    public static final String PAYMENT_URL = "http://localhost:8001/payment";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/payment";


    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResilt<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/create", payment, CommonResilt.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResilt<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/get/"+id, CommonResilt.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResilt<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResilt> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/get/" + id, CommonResilt.class);
        boolean xxSuccessful = forEntity.getStatusCode().is2xxSuccessful();
        if (xxSuccessful) {
            return forEntity.getBody();
        } else {
            return new CommonResilt<>(444, "操作失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String zipkin() {
        return restTemplate.getForObject(PAYMENT_URL + "/zipkin", String.class);
    }

}
