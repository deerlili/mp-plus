package com.deerlili.mp.controller;


import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.dto.ReturnT;
import com.deerlili.mp.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/get")
    @ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户" )
    public ReturnT<User> selectUserById() {
        ReturnT<User> returnT = new ReturnT<>();
        returnT.setContent(userMapper.selectById(1));
        return returnT;
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", notes = "添加用户" )
    public String registerUser(@RequestBody User registerUser){
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setRoleId(0L);
        userMapper.insert(user);
        return "success";
    }

}

