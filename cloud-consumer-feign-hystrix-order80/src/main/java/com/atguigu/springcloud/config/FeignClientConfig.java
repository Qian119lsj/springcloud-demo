package com.atguigu.springcloud.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FeignClientConfig {
    // 连接超时
    @Value("${service.feign.connectTimeout:60000}")
    private int connectTimeout;

    // 数据读取超时
    @Value("${service.feign.readTimeOut:60000}")
    private int readTimeout;

    // 构造自定义配置类
    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }
}
