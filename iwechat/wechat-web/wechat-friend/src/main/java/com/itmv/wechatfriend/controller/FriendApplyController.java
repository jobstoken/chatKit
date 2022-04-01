package com.itmv.wechatfriend.controller;


import com.itmv.entity.FriendApply;
import com.itmv.entity.User;
import com.itmv.entity.base.ResultEntity;
import com.itmv.service.FriendApplyService;
import com.itmv.service.FriendService;
import com.itmv.wechatfriend.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/apply")
public class FriendApplyController {

    @Autowired
    private FriendApplyService friendApplyService;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;


    @RequestMapping(value = "/add")
    public ResultEntity addFriend(FriendApply apply) {
        System.out.println(apply);
        User tUser = userService.getUserById(apply.getTid());
        User fUser = userService.getUserById(apply.getFid());
        if (tUser != null) {
            if (tUser.equals(fUser)) {
                return ResultEntity.error("不可以加自己为好友");
            }
            apply.setCreateTime(new Date());
            apply.setStatus(1);
            friendApplyService.insert(apply);
            return ResultEntity.success();
        } else {
            return ResultEntity.error("此用户不存在");
        }
    }

    @RequestMapping(value = "/applies")
    public ResultEntity getFriendApplies(Integer uid) {
        System.out.println("hahfsjdfbjdsabfas");
        System.out.println("uid: " + uid);
        List<FriendApply> applies = friendApplyService.getApplyList(uid);
        for (FriendApply apply : applies) {
            Integer fid = apply.getFid();
            User user = userService.getUserById(fid);
            user.setPassword(null);
            System.out.println(user);
            apply.setFriend(user);
        }
        return ResultEntity.success(applies);
    }


    /**
     * 如果是拒绝只修改状态
     * 如果是接受，修改状态并且还要添加好友
     * @param status
     * @param id
     * @return
     */
    @RequestMapping(value = "/update")
    public ResultEntity updateFriendApply(Integer status, Integer id) {

        FriendApply apply = (FriendApply) friendApplyService.selectById(id);
        apply.setStatus(status);
        friendApplyService.update(apply);
        if (status == 2){
            friendService.addFriend(apply.getTid(), apply.getFid(), 1);
        }

        return ResultEntity.success();
    }





}
