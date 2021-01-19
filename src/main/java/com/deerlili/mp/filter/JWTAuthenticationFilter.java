package com.deerlili.mp.filter;

import com.alibaba.fastjson.JSON;
import com.deerlili.mp.dto.JwtUser;
import com.deerlili.mp.dto.LoginUser;
import com.deerlili.mp.dto.ReturnT;
import com.deerlili.mp.util.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *  该拦截器用于获取用户登录的信息
 *  只需创建一个token并调用authenticationManager.authenticate()
 *  让spring-security去进行验证就可以了
 *  不用自己查数据库再对比密码了，这一步交给spring去操作
 *
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 ,
 * attemptAuthentication：接收并解析用户凭证。
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token并返回。
 */

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Long> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser =
                    new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);

            rememberMe.set(loginUser.getRememberMe());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            logger.error("attemptAuthentication error :{}", e);
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        boolean isRemember = rememberMe.get() == 1;

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }

        String token = JwtTokenUtils.createToken(jwtUser.getId(), jwtUser.getUsername(), role, isRemember);
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> maps = new HashMap<>();
        maps.put("data", JwtTokenUtils.TOKEN_PREFIX + token);
        maps.put("roles", role.split(","));
        response.getWriter().write(JSON.toJSONString(new ReturnT<>(maps)));
    }

    // 验证失败
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}