package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常 ok
     * @param id
     * @return
     */
    public String paymentInfo(Integer id) {
        return "线程池: "+ Thread.currentThread().getName()+" paymentInfo_ok,id: "+ id+"\t 哈哈~";
    }


    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfoTimeout(Integer id) {
        int time = 2000;
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池: "+ Thread.currentThread().getName()+" paymentInfo_Timeout,id: "+ id+"\t 哈哈~"+"超时"+time;
    }

    public String paymentInfoTimeoutHandler(Integer id) {
        return "线程池: "+ Thread.currentThread().getName()+" paymentInfo_Timeout,id: "+ id+"\t 55555~";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败阈值
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {

        if (id < 0) {
            throw new RuntimeException(" id 不能为负数!!");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t 调用成功,流水号: " + serialNumber;

    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能为负数,id: " + id;

    }
}
