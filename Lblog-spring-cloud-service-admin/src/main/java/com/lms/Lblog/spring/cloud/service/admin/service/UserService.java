package com.lms.Lblog.spring.cloud.service.admin.service;


import com.lms.Lblog.spring.cloud.service.admin.po.User;

/**
 * Autor Lms
 * Time 2019/9/1/001
 */

public interface UserService {
    User checkUser(String username, String password);

    User getUserByname(String name);

    User saveUser(User user);
}
