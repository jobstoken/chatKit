package com.itmv.wechatuser.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.itmv.entity.User;
import com.itmv.service.UserService;
import com.itmv.wechatuser.mapper.UserMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<User> {

    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User val) {
        return userMapper.insert(val);
    }

    @Override
    public int update(User val) {
        return userMapper.updateById(val);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public List<User> getList() {
        return userMapper.selectList(null);
    }

    @Override
    public void getPage(Page<User> page) {
        List<User> list = userMapper.selectPage(page, null);
        page.setRecords(list);
    }

    @Override
    public Page<User> getPageRep(Page<User> page) {
        List<User> list = userMapper.selectPage(page, null);
        page.setRecords(list);
        return page;
    }

    @Override
    public Integer register(User user) {
        // 先判断用户是否存在
        if (isContains("username", user.getUsername())){
            return 1;
        } else if (isContains("phone", user.getPhone())){
            return 2;
        } else {
            if (user.getNickname().equals("")){
                user.setNickname(user.getUsername());
            }
            userMapper.insert(user);
            return 3;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }

    public Boolean isContains(String column, String value) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq(column, value);
        return userMapper.selectCount(wrapper) != 0;
    }
}
