package com.itmv.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.itmv.entity.Friend;

import java.util.List;

public interface FriendService<T> {
    int insert(T val);

    int update(T val);

    T selectById(Integer id);

    int deleteById(Integer id);

    List<T> getList();

    void getPage(Page<T> val);

    Page<T> getPageRep(Page<T> val);

    void addFriend(Integer tid, Integer fid, int i);

    boolean checkRelationship(Integer uid, Integer tid);

    List<Friend> getFriendsById(Integer uid);
}
