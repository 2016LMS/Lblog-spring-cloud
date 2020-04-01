package com.lms.Lblog.spring.cloud.web.admin.feign.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Autor Lms
 * Time 2019/9/15/015
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("user")==null) {
            response.sendRedirect("/admin");    //重定向的路径为: http://localhost:8080/admin
            return false;
        }
        return true;

    }
}
