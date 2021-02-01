package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.serice.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback="paymentGlobalFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("consumer/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_ok(id);
    }


    /*@HystrixCommand(fallbackMethod = "paymentTimeoutFallbackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })*/
    @GetMapping("consumer/hystrix/timeout/{id}")
    public String paymentTimeout(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentTimeout(id);
    }

    public String paymentTimeoutFallbackMethod(Integer id) {
        return "我是消费者80,请10秒后在世或自己运行出错 5  55~";
    }


    //全局fallback方法
    public String paymentGlobalFallbackMethod() {
        return "Global异常处理信息,请稍后再试 5  55~";
    }


}
