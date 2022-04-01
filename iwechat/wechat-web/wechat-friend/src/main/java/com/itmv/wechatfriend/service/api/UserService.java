package com.itmv.wechatfriend.service.api;


import com.itmv.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "USER-WEB")
public interface UserService {

    @RequestMapping(value = "/user/getUserById")
    public User getUserById(@RequestParam("id") Integer id);

}
