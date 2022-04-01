package com.itmv.wechatuser.service.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "FRIEND-WEB")
public interface FriendService {

    @RequestMapping(value = "/api/getFriendById")
    public Boolean getFriendById(@RequestParam("uid") Integer uid, @RequestParam("tid") Integer tid);
}
