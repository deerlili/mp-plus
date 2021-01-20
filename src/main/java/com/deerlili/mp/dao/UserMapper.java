package com.deerlili.mp.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deerlili.mp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author deerlili
 * @since 2021-01-19
 */
public interface UserMapper extends BaseMapper<User> {

    // 用户登录
    User loadByUserName(@Param("username") String username);
    // 自定义SQL
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
    // 自定义分页
    IPage<User> selectUserPage(Page<User> page
            , @Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
