package com.itmv.wechatfriend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.itmv.entity.Friend;
import com.itmv.service.FriendService;
import com.itmv.wechatfriend.mapper.FriendMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService<Friend> {

    @Resource
    private FriendMapper friendMapper;

    @Override
    public int insert(Friend val) {
        return friendMapper.insert(val);
    }

    @Override
    public int update(Friend val) {
        return friendMapper.updateById(val);
    }

    @Override
    public Friend selectById(Integer id) {
        return friendMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return friendMapper.deleteById(id);
    }

    @Override
    public List<Friend> getList() {
        return friendMapper.selectList(null);
    }

    @Override
    public void getPage(Page<Friend> page) {
        List<Friend> list = friendMapper.selectPage(page, null);
        page.setRecords(list);
    }

    @Override
    public Page<Friend> getPageRep(Page<Friend> page) {
        List<Friend> list = friendMapper.selectPage(page, null);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addFriend(Integer tid, Integer fid, int i) {
        Friend friend1 = new Friend();
        friend1.setUid(tid);
        friend1.setFid(fid);
        friend1.setStatus(1);
        insert(friend1);

        Friend friend2 = new Friend();
        friend2.setUid(fid);
        friend2.setFid(tid);
        friend2.setStatus(1);
        insert(friend2);
    }

    @Override
    public boolean checkRelationship(Integer uid, Integer tid) {
        EntityWrapper wrapper1 = new EntityWrapper();
        wrapper1.eq("uid", uid);
        wrapper1.eq("fid", tid);
        wrapper1.eq("status", 1);

        EntityWrapper wrapper2 = new EntityWrapper();
        wrapper2.eq("uid", tid);
        wrapper2.eq("fid", uid);
        wrapper2.eq("status", 1);

        return (friendMapper.selectCount(wrapper1) == 1) || (friendMapper.selectCount(wrapper2) == 1);
    }

    @Override
    public List<Friend> getFriendsById(Integer uid) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("uid", uid);
        return friendMapper.selectList(wrapper);
    }
}
