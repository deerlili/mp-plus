package com.deerlili.mp;

import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author deerlili
 * @version v1.0
 * @description AR模式
 * @Time 2021-01-20 13:33
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    @Test
    public void insertTest() {
        User user= new User();
        user.setUsername("test");
        user.setPassword("test");
        boolean insert = user.insert();
    }

    @Test
    public void selectTest() {
        User user= new User();
        User user1 = user.selectById(1L);

        user.setId(1L);
        User user2 = user.selectById();
    }

    @Test
    public void updateTest() {
        User user= new User();
        user.setId(1L);
        user.setUsername("123");
        boolean user2 = user.updateById();
    }

    @Test
    public void deleteTest() {
        User user= new User();
        user.setId(1L);
        boolean user2 = user.deleteById();
    }

    @Test
    public void insertOrUpdateTest() {
        User user= new User();
        user.setId(1L);
        user.setUsername("cmtest");
        boolean user2 = user.insertOrUpdate();
    }
}
