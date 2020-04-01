package com.lms.Lblog.spring.cloud.web.admin.feign.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Autor Lms
 * Time 2019/9/15/015
 */
@Configuration //这个注解声明这个类为配置类
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")   //过滤http://localhost:8080/admin/** 所有路径
                .excludePathPatterns("/admin")  //不会过滤http://localhost:8080/admin 路径
                .excludePathPatterns("/admin/login")  //不会过滤http://localhost:8080/admin/login 路径
                .excludePathPatterns("/admin/register"); ////不会过滤http://localhost:8080/admin/register 路径

    }
}
