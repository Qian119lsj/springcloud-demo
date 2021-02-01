package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {

    @Autowired
    private IMessageProvider messageProvider;


    @GetMapping(value = "/sendMsg")
    public String sendMsg() {
        return messageProvider.send();
    }
}
