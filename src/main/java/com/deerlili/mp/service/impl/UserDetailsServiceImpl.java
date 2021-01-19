package com.deerlili.mp.service.impl;

import com.deerlili.mp.dao.UserMapper;
import com.deerlili.mp.entity.JwtUser;
import com.deerlili.mp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl
 *
 * @author jingwk
 * @version v2.1.1
 * @since 2019-03-15
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper UserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = UserMapper.loadByUserName(s);
        return new JwtUser(user);
    }

}
