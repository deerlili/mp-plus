package com.deerlili.mp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author deerlili
 * @version v1.0
 * @description MyBatisPlus配置
 * @Time 2021-01-17 21:27
 */
@Configuration
public class MyBatisPlusConfig {

    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
