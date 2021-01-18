package com.deerlili.mp.controller;

import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author deerlili
 * @version v1.0
 * @description 用户接口
 * @Time 2021-01-18 20:58
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public User getUserById() {
        User user = userMapper.selectById(1L);
        return user;
    }
}
