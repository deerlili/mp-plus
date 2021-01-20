package com.deerlili.mp;

import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author deerlili
 * @version v1.0
 * @description 代码测试类
 * @Time 2021-01-15 23:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> users = userMapper.selectList(null);
        // 判断数据条数
        Assert.assertEquals(4,users.size());
        users.forEach(System.out::println);
    }
}
