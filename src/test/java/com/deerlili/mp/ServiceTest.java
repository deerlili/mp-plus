package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deerlili.mp.entity.User;
import com.deerlili.mp.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Wrapper;
import java.util.Arrays;
import java.util.List;

/**
 * @author deerlili
 * @version v1.0
 * @description
 * @Time 2021-01-20 17:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private IUserService userService;
    
    @Test
    public void getOne() {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.gt(User::getAge,25);
        // 查询超过一条会报错
        User one1 = userService.getOne(queryWrapper);
        // 查询多条返回第一条
        User one2 = userService.getOne(queryWrapper,false);
        System.out.println(one1);
    }

    @Test
    public void batch() {
        User user1 = new User();
        user1.setUsername("mpadmin");
        User user2 = new User();
        user2.setUsername("mptest");
        List<User> users = Arrays.asList(user1, user2);
        boolean b1 = userService.saveBatch(users); // 默认是1000一起提交
        boolean b2 = userService.saveBatch(users, 50);
        boolean b3 = userService.saveOrUpdateBatch(users); // 默认是1000一起提交
        boolean b4 = userService.saveOrUpdateBatch(users, 50);
    }

    @Test
    public void chain() {
        // 查询
        userService.lambdaQuery().gt(User::getAge,25)
                .like(User::getUsername,"mp").list();
        // 更新
        userService.lambdaUpdate().eq(User::getAge,25)
                .set(User::getUsername,"mp_tt").update();
        // 删除
        userService.lambdaUpdate().eq(User::getAge,25).remove();
    }
}
