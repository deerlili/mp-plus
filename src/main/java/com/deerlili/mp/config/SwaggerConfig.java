package com.deerlili.mp.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** Swagger2API文档的配置 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnWebApplication
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.deerlili.mp.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.paths(PathSelectors.regex("/.*"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MP Web Api Docs")
                .description("MP后端接口文档说明")
                .contact(contact())
                .termsOfServiceUrl("http://localhost:8080")
                .version("v.2.1")
                .license("@重庆半山智能科技有限公司")
                .licenseUrl("baidu.com")
                .build();
    }

    private Contact contact() {
        return new Contact("@重庆半山智能科技有限公司"
                , "http://www.cqbanshan.com"
                , "deerlili@outlook.com");
    }

}
