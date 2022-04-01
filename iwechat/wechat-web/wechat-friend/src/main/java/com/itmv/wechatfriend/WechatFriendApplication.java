package com.itmv.wechatfriend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.itmv.wechatfriend")
@MapperScan(basePackages = "com.itmv.wechatfriend.mapper")
@EnableFeignClients
public class WechatFriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatFriendApplication.class, args);
    }

}
