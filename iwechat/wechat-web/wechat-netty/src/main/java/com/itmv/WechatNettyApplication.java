package com.itmv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.itmv", exclude = DataSourceAutoConfiguration.class)
public class WechatNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatNettyApplication.class, args);
    }

}
