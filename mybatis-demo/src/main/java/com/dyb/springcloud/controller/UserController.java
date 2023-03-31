package com.dyb.springcloud.controller;

import com.dyb.springcloud.pojo.ForexExposure;
import com.dyb.springcloud.pojo.User;
import com.dyb.springcloud.service.ExposureService;
import com.dyb.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ExposureService exposureService;

    @RequestMapping
    public List<User> getAll() {
        return userService.queryAllUser();
    }

    @GetMapping("/ex/findAll")
    public List<ForexExposure> findAll() {
        return exposureService.findAll();
    }

    @PostMapping(value = "/ex/insert", consumes = "application/json;charset=utf-8")
    public int insert(@RequestBody ForexExposure exposure) {
        return exposureService.insert(exposure);
    }
}
