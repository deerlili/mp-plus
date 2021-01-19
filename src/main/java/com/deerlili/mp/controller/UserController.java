package com.deerlili.mp.controller;


import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.JwtUser;
import com.deerlili.mp.entity.ReturnT;
import com.deerlili.mp.entity.User;
import com.deerlili.mp.service.impl.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author deerlili
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/mp/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/get")
    @ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户" )
    public ReturnT<User> selectUserById() {
        ReturnT<User> returnT = new ReturnT<>();
        returnT.setContent(userMapper.selectById(1));
        return returnT;
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户", notes = "注册用户" )
    public String registerUser(@RequestBody User registerUser){
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setRoleId(0L);
        userMapper.insert(user);
        return "success";
    }

}

