package com.deerlili.mp;

import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author deerlili
 * @version v1.0
 * @description 数据插入类
 * @Time 2021-01-15 23:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUserName("小王");
        user.setAge(23);
        user.setManagerId(2L);
        int inserts = userMapper.insert(user);
        System.out.println("影响记录数："+inserts);
    }
}
