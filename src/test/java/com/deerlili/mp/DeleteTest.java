package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author deerlili
 * @version v1.0
 * @description 删除数据测试类
 * @Time 2021-01-18 16:16
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        int i = userMapper.deleteById(1350294244945870850L);
        System.out.println("删除记录数：" + i);
    }

    @Test
    public void deleteBatchIds() {
        int i = userMapper.deleteBatchIds(Arrays.asList(5,6));
        System.out.println("删除记录数：" + i);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("name","小器2");
        hashMap.put("age","28");
        int i = userMapper.deleteByMap(hashMap);
        System.out.println("删除记录数：" + i);
    }

    @Test
    public void deleteByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("id",5).eq("name","小胖");

        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getId,5).eq(User::getUserName,"小胖");

        int i = userMapper.delete(queryWrapper);
    }
}
