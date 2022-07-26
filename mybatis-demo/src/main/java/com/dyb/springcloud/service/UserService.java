package com.dyb.springcloud.service;

import com.dyb.springcloud.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> queryAllUser();
}
