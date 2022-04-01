package com.itmv.wechatfriend.controller;


import com.itmv.entity.Friend;
import com.itmv.entity.base.ResultEntity;
import com.itmv.service.FriendService;

import com.itmv.wechatfriend.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getFriendById")
    public Boolean getFriendById(Integer uid, Integer tid) {
        if (uid != null && tid != null){
            return friendService.checkRelationship(uid, tid);
        }
        return false;
    }

    @RequestMapping(value = "/friends")
    public ResultEntity getFriends(Integer uid){
        List<Friend> list = friendService.getFriendsById(uid);
        for (Friend relation : list) {
            relation.setFriend(userService.getUserById(relation.getFid()));
        }
        list.sort((o1, o2) -> o1.getFriend().getPinyin().compareTo(o2.getFriend().getPinyin()));
        return ResultEntity.success(list);
    }

}
