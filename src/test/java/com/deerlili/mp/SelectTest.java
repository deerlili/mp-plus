package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

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

        /*
         * 键是数据库中的列
         */
        columnMap.put("age",30);
        columnMap.put("manager_id",1);
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper1() {
        /*
         * 有三种方式：
         *      QueryWrapper<User> userWrapper = new QueryWrapper<User>();
         *      QueryWrapper<User> query = Wrappers.<User>query();
         *
         */
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * @column 数据库中的列名
         * 查询 name like '%器%' and age < 32
         */
        userWrapper.like("name","器").lt("age",32);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
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
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
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

    @Test
    public void selectByWrapper4() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * date_format(create_time,'%Y-%m-%d') and manager_id in
         * (select id from mp_user where name like '%碗%')
         * # 有sql注入的风险
         * apply("date_format(create_time,'%Y-%m-%d') = '2020-08-12'")
         * # 建议使用第二种
         * apply("date_format(create_time,'%Y-%m-%d') = {0}", "2020-08-12")
         */
        userWrapper
                .apply("date_format(create_time,'%Y-%m-%d') = '2020-08-12' or true")
                .inSql("manager_id","select id from mp_user where name like '%碗%'");
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper5() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * name like '小%' and (age < 40 or email is not null)
         * 使用 Lambda 表达式
         */
        userWrapper.likeRight("name","小")
                .and(wq -> wq
                        .lt("age",40)
                        .or().isNotNull("email"));
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * name like '小%' or (age < 28 and age > 40 and email is not null)
         * 使用lambda表达式
         */
        userWrapper.likeRight("name","小")
                .or(wq -> wq
                        .lt("age",28)
                        .gt("age",40)
                        .isNotNull("email"));
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper7() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * (age < 40 or email is not null) and name like '小%'
         * 使用lambda表达式
         */
        userWrapper.nested(wq -> wq.lt("age",40).or().isNotNull("email"))
                .likeRight("name","小");
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper8() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * age in (25,30,40)
         */
        userWrapper.in("age",Arrays.asList(25,30,40));
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper9() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * limit 1
         * 备注：有SQL注入风险，不是重要表可以使用
         */
        userWrapper.last("limit 1");
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperSupper1() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * 查询满足条件 name like '%器%' and age < 32 的
         * id 和 name 字段
         */
        userWrapper.select("id","name")
                .like("name","器").lt("age",32);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperSupper2() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * 查询满足条件 name like '%器%' and age < 32 的
         * 除了 id 和 name 字段
         */
        userWrapper.like("name","器").lt("age",32)
                .select(User.class, info -> !info.getColumn().equals("id")
                        && !info.getColumn().equals("name"));
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    private void selectByWrapperSupper3Condition(String name) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * 以前的写法
         */
        if (!StringUtils.isEmpty(name)) {
            userWrapper.eq("name",name);
        }
        /*
         * 现在的写法
         */
        userWrapper.eq(!StringUtils.isEmpty(name),"name",name);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }
    @Test
    public void selectByWrapperSupper3() {
        String name = "";
        selectByWrapperSupper3Condition(name);
    }
}
