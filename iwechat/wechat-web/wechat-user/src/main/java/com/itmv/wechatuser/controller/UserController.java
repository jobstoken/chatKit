package com.itmv.wechatuser.controller;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import com.itmv.entity.User;
import com.itmv.entity.base.ResultEntity;
import com.itmv.entity.netty.OutLineMessage;
import com.itmv.service.UserService;
import com.itmv.wechatuser.service.api.FriendService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FastFileStorageClient ffsc;

    @Autowired
    private FriendService friendService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fastpath}")
    private String fdfspath;

    @RequestMapping(value = "/register")
    public ResultEntity register(User user) {
        System.out.println("register : " + user);
        Integer res = userService.register(user);
        if (res == 1) {
            return ResultEntity.error("用户名已存在");
        } else if (res == 2) {
            return ResultEntity.error("手机号已被注册");
        } else {
            return ResultEntity.success("注册成功");
        }
    }

    /**
     *
     * @param username
     * @param password
     * @param id  设备id
     * @return
     */
    @RequestMapping(value = "/login")
    public ResultEntity login(String username, String password, String id) {
        User user = userService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setPassword(null);                 // 移动端不能存储密码

            // redis中根据用户id找到deviceId
            String deviceId = redisTemplate.opsForValue().get(user.getId().toString());
            if (deviceId != null && !deviceId.equals(id)){
                // 挤下线其他设备，将下线信息交给交换机
                OutLineMessage outLineMessage = new OutLineMessage();
                outLineMessage.setId(deviceId);
                rabbitTemplate.convertAndSend("ws_exchange", "", outLineMessage);
            }
            redisTemplate.opsForValue().set(user.getId().toString(), id);

            return ResultEntity.success(user);      // user用于移动端存储
        } else {
            return ResultEntity.error("用户名或密码错误!");
        }
    }

    @RequestMapping(value = "/upload")
    public ResultEntity upload(Integer userId, MultipartFile file) {
        System.out.println("userId:" + userId);
        System.out.println(file);
        System.out.println("**********************");
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        assert filename != null;
        // 获取文件后缀
        String fileExName = filename.substring(filename.lastIndexOf(".") + 1);
        try {
            // 上传到FastDFS
            StorePath storePath = ffsc.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), fileExName, null);
            String fullPath = storePath.getFullPath();
            // 获取大图小图
            String headerBig = fdfspath + "/" + fullPath;
            String headerSmall = fdfspath + "/" + fullPath.replace(".", "_100x100.");
            // 更换大图小图
            if (userId != null) {
                User user = (User) userService.selectById(userId);
                if (user != null) {
                    user.setHeaderBig(headerBig);
                    user.setHeaderSmall(headerSmall);
                    userService.update(user);
                    Map<String, String> map = new HashMap<>();
                    map.put("headerBig", headerBig);
                    map.put("headerSmall", headerSmall);
                    return ResultEntity.success(map);
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/search")
    public ResultEntity search(String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResultEntity.error("此用户不存在");
        }
        user.setPassword(null);
        return ResultEntity.success(user);
    }

    @RequestMapping(value = "/getUserById")
    public User getUserById(Integer id) {
        System.out.println("id : " + id);
        if (id != null) {
            User user = (User) userService.selectById(id);
            return user;
        }
        return null;
    }


    @RequestMapping(value = "/check")
    public ResultEntity check(Integer uid, Integer tid) {
        System.out.println("uid: " + uid + " tid: " + tid);

        Boolean tag = friendService.getFriendById(uid, tid);

        if (tag) {
            return ResultEntity.error("已加为好友，不可重复添加");
        } else {
            return ResultEntity.success();
        }
    }


    @RequestMapping(value = "/findUserById")
    public ResultEntity findUserById(Integer id){
        User user = (User) userService.selectById(id);
        return ResultEntity.success(user);
    }
}














