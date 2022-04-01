package com.itmv.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.itmv.entity.FriendApply;
import com.itmv.entity.User;

import java.util.List;

public interface FriendApplyService<T> {
    int insert(T val);

    int update(T val);

    T selectById(Integer id);

    int deleteById(Integer id);

    List<T> getList();

    void getPage(Page<T> val);

    Page<T> getPageRep(Page<T> val);

    List<T> getApplyList(Integer uid);
}
