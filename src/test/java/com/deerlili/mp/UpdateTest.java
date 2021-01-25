package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author deerlili
 * @version v1.0
 * @description 数据更新测试类
 * @Time 2021-01-18 15:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById() {
        User user = new User();
        user.setId(4L);
        user.setEmail("update@163.com");
        // 乐观锁实现
        user.setVersion(0L); // 这里0假设是从数据库查询出来的
        int i = userMapper.updateById(user);
        System.out.println("影响记录数据：" + i);
    }

    @Test
    public void updateByWrapper1() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 判断条件
        updateWrapper.eq("username","小器3").eq("age",32);
        // 更新实体
        User user = new User();
        user.setEmail("update@163.com");
        user.setAge(30);
        int i = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数据：" + i);
    }

    @Test
    public void updateByWrapper2() {
        User whereUser = new User();
        whereUser.setUsername("小器3");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
        // 判断条件
        updateWrapper.eq("username","小器3").eq("age",30);
        // 更新实体
        User user = new User();
        user.setEmail("31@163.com");
        user.setAge(31);
        int i = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数据：" + i);
    }

    @Test
    public void updateByWrapper3() {
        User whereUser = new User();
        whereUser.setUsername("小器3");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
        updateWrapper.eq("username","小器3")
                .eq("age",31)
                .set("age",33)
                .set("email","2020@deerlili.com");
        int i = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数据：" + i);
    }

    @Test
    public void updateByWrapperLamdba() {
        // LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(User::getUsername,"小器1")
                .eq(User::getAge,25)
                .set(User::getEmail,"tte@163.com")
                .set(User::getAge,32);
        userMapper.update(null, updateWrapper);
    }

    @Test
    public void updateByWrapperLamdbaChaine() {
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
                .eq(User::getUsername, "小器1")
                .eq(User::getAge, 32)
                .set(User::getEmail, "tte1@163.com")
                .set(User::getAge, 33).update();
    }
}
