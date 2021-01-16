package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
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
public class SelectTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        User user = userMapper.selectById(1);
        System.out.println("实体记录："+user);
    }

    @Test
    public void selectByIds() {
        List<Integer> asList = Arrays.asList(1, 2, 3);
        List<User> userList = userMapper.selectBatchIds(asList);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String,Object> columnMap = new HashMap<>();
        /**
         * 键是数据库中的列
         */
        columnMap.put("age",30);
        columnMap.put("manager_id",1);
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper1() {
        /**
         * 有三种方式：
         *      QueryWrapper<User> userWrapper = new QueryWrapper<User>();
         *      QueryWrapper<User> query = Wrappers.<User>query();
         *
         */
        QueryWrapper<User> userWrapper = new QueryWrapper<User>();
        /**
         * @column 数据库中的列名
         * 查询 name like '%器%' and age < 32
         */
        userWrapper.like("name","器").lt("age",32);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> userWrapper = new QueryWrapper<User>();
        /**
         * 查询 name like '%器%' and age between 20 and 40 and email is not null
         */
        userWrapper.like("name","器")
                .between("age",20,40)
                .isNotNull("email");
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper3() {
        QueryWrapper<User> userWrapper = new QueryWrapper<User>();
        /**
         * 查询
         * name like '小%' or age >= 20 order by age desc,id asc
         */
        userWrapper.likeLeft("name","小")
                .or()
                .ge("age",20)
                .orderByDesc("age")
                .orderByAsc("id");
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }
}
