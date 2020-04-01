package com.lms.Lblog.spring.cloud.service.admin.service.impl;

import com.lms.Lblog.spring.cloud.service.admin.dao.UserRepository;
import com.lms.Lblog.spring.cloud.service.admin.po.User;
import com.lms.Lblog.spring.cloud.service.admin.service.UserService;
import com.lms.Lblog.spring.cloud.service.admin.utility.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Autor Lms
 * Time 2019/9/1/001
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password){
        User user= userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
