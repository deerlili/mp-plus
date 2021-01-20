package com.deerlili.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
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
        System.out.println("实体记录：" + user);
    }

    @Test
    public void selectByIds() {
        List<Integer> asList = Arrays.asList(1, 2, 3);
        List<User> userList = userMapper.selectBatchIds(asList);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> columnMap = new HashMap<>();

        /*
         * 键是数据库中的列
         */
        columnMap.put("age", 30);
        columnMap.put("manager_id", 1);
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
        userWrapper.like("name", "器").lt("age", 32);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * 查询 name like '%器%' and age between 20 and 40 and email is not null
         */
        userWrapper.like("username", "器")
                .between("age", 20, 40)
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
        userWrapper.likeLeft("username", "小")
                .or()
                .ge("age", 20)
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
                .inSql("manager_id", "select id from mp_user where username like '%碗%'");
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
        userWrapper.likeRight("username", "小")
                .and(wq -> wq
                        .lt("age", 40)
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
        userWrapper.likeRight("username", "小")
                .or(wq -> wq
                        .lt("age", 28)
                        .gt("age", 40)
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
        userWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email"))
                .likeRight("username", "小");
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper8() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * age in (25,30,40)
         */
        userWrapper.in("age", Arrays.asList(25, 30, 40));
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
        userWrapper.select("id", "username")
                .like("username", "器").lt("age", 32);
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
        userWrapper.like("username", "器").lt("age", 32)
                .select(User.class, info -> !info.getColumn().equals("id")
                        && !info.getColumn().equals("username"));
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    private void selectByWrapperSupper3Condition(String username) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /*
         * 以前的写法
         */
        if (!StringUtils.isEmpty(username)) {
            userWrapper.eq("username", username);
        }
        /*
         * 现在的写法
         */
        userWrapper.eq(!StringUtils.isEmpty(username), "username", username);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperSupper3() {
        String name = "";
        selectByWrapperSupper3Condition(name);
    }

    @Test
    public void selectByWrapperEntity1() {
        User userWhere = new User();
        userWhere.setUsername("小");
        userWhere.setAge(30);
        QueryWrapper<User> userWrapper = new QueryWrapper<>(userWhere);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEntity2() {
        User userWhere = new User();
        userWhere.setUsername("小胖");
        userWhere.setAge(30);
        QueryWrapper<User> userWrapper = new QueryWrapper<>(userWhere);
        userWrapper.select("id", "name")
                .like("name", "器").lt("age", 32);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", "小莫");
        userMap.put("age", null);
        // userWrapper.allEq(userMap);
        // userWrapper.allEq(userMap,false);
        userMap.put("email", 30);
        // userWrapper.allEq((k, v) -> k.equals("age"), userMap);
        // userWrapper.allEq((k, v) -> !k.equals("age"), userMap);
        // userWrapper.allEq((k, v) -> !v.equals(30), userMap);
        userWrapper.allEq((k, v) -> !v.equals(30), userMap, false);
        List<User> userList = userMapper.selectList(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps1() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.select("id","username").like("username", "器")
                .between("age", 20, 40);
        List<Map<String, Object>> userList = userMapper.selectMaps(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        /**
         * SELECT
         * 	avg(age) avg_age,min(age) min_age,max(age) max_age
         * FROM `mp_user`
         * group by manager_id
         * having sum(age) < 100
         */
        userWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id")
                .having("sum(age)<{0}",100);
        List<Map<String, Object>> userList = userMapper.selectMaps(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.select("id","username").like("username", "器")
                .between("age", 20, 40);
        List<Object> userList = userMapper.selectObjs(userWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectCount() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.like("username", "器")
                .between("age", 20, 40);
        Integer integer = userMapper.selectCount(userWrapper);
        System.out.println(integer);
    }

    @Test
    public void selectOne() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.like("username", "小器1")
                .between("age", 20, 40);
        User integer = userMapper.selectOne(userWrapper);
        System.out.println(integer);
    }

    @Test
    public void selectLambda1() {
        /**
         * 有三种创建方式
         *  LambdaQueryWrapper<User> userWrapper = new QueryWrapper<User>().lambda();
         *  LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
         *  LambdaQueryWrapper<User> objectLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
         */
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        // 这样写防止column写错
        userLambdaQueryWrapper.like(User::getUsername,"器").lt(User::getAge,40);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda2() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        // 这样写防止column写错
        userLambdaQueryWrapper.likeRight(User::getUsername,"小")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda3() {
        LambdaQueryChainWrapper<User> chainWrapper = new LambdaQueryChainWrapper<User>(userMapper)
                .like(User::getUsername, "小")
                .lt(User::getAge, 40);
        List<User> users = userMapper.selectList(chainWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectSql() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        // 这样写防止column写错
        userLambdaQueryWrapper.likeRight(User::getUsername,"小")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> users = userMapper.selectAll(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }
}
