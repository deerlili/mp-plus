package com.deerlili.mp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author deerlili
 * @version v1.0
 * @Description 前端静态资源访问
 * 跨域访问
 * @Time 2021-01-14 00:08
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
                .addResourceLocations("classpath:/static/index.html");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/static/");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
