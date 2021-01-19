package com.deerlili.mp.dao;

import com.deerlili.mp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author deerlili
 * @since 2021-01-19
 */
public interface UserMapper extends BaseMapper<User> {

    User loadByUserName(@Param("username") String username);

}
