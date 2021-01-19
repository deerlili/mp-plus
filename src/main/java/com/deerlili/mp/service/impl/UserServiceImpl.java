package com.deerlili.mp.service.impl;

import com.deerlili.mp.entity.User;
import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author deerlili
 * @since 2021-01-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
