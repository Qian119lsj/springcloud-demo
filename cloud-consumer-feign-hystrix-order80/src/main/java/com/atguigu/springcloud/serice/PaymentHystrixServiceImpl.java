package com.atguigu.springcloud.serice;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return null;
    }

    @Override
    public String paymentTimeout(Integer id) {
        return "我是消费者80,请10秒后在世或自己运行出错 5  55~";
    }
}
