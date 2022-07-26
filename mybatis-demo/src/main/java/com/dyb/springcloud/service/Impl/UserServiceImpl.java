package com.dyb.springcloud.service.Impl;

import com.dyb.springcloud.mapper.UserMapper;
import com.dyb.springcloud.pojo.User;
import com.dyb.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }
}
