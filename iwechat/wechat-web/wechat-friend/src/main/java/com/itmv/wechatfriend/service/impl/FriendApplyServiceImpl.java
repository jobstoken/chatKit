package com.itmv.wechatfriend.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.itmv.entity.FriendApply;
import com.itmv.service.FriendApplyService;
import com.itmv.wechatfriend.mapper.FriendApplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendApplyServiceImpl implements FriendApplyService<FriendApply> {

    @Resource
    private FriendApplyMapper friendApplyMapper;

    @Override
    public int insert(FriendApply val) {
        return friendApplyMapper.insert(val);
    }

    @Override
    public int update(FriendApply val) {
        return friendApplyMapper.updateById(val);
    }

    @Override
    public FriendApply selectById(Integer id) {
        return friendApplyMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return friendApplyMapper.deleteById(id);
    }

    @Override
    public List<FriendApply> getList() {
        return friendApplyMapper.selectList(null);
    }

    @Override
    public void getPage(Page<FriendApply> page) {
        List<FriendApply> list = friendApplyMapper.selectPage(page, null);
        page.setRecords(list);
    }

    @Override
    public Page<FriendApply> getPageRep(Page<FriendApply> page) {
        List<FriendApply> list = friendApplyMapper.selectPage(page, null);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<FriendApply> getApplyList(Integer uid) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("tid", uid);
        return friendApplyMapper.selectList(wrapper);
    }

}
