package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author deerlili
 * @version v1.0
 * @description 数据插入类
 * @Time 2021-01-15 23:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SelectPageTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectPage1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",25);
        // false 不开启统计信息，总页数和总记录数等
        Page<User> iPage = new Page<>(1,3, true);
        IPage<User> userIPage = userMapper.selectPage(iPage, queryWrapper);
        System.out.println("总页数：" + userIPage.getPages());
        System.out.println("总记录：" + userIPage.getTotal());
        List<User> userList = userIPage.getRecords();
        userList.forEach(System.out::println);
    }

    @Test
    public void selectPage2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("age",40);
        IPage<Map<String, Object>> page = new Page<Map<String, Object>>(1,5);
        IPage<Map<String, Object>> userIPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println("总页数：" + userIPage.getPages());
        System.out.println("总记录：" + userIPage.getTotal());
        List<Map<String, Object>> userList = userIPage.getRecords();
        userList.forEach(System.out::println);
    }

    @Test
    public void selectPage3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("age",40);
        Page<User> iPage = new Page<>(1,3, false);
        IPage<User> userIPage = userMapper.selectUserPage(iPage, queryWrapper);
        System.out.println("总页数：" + userIPage.getPages());
        System.out.println("总记录：" + userIPage.getTotal());
        List<User> userList = userIPage.getRecords();
        userList.forEach(System.out::println);
    }

}
