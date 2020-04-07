package com.lms.Lblog.spring.cloud.service.admin.controller.admin;

import com.lms.Lblog.spring.cloud.service.admin.po.User;
import com.lms.Lblog.spring.cloud.service.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Autor Lms
 * Time 2020-4-2
 */
@RestController
public class UserServiceController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginCheck",method = RequestMethod.POST)
    public User checkUser(String username, String password){
        return userService.checkUser(username,password);
    }

    @RequestMapping(value = "/userByName",method = RequestMethod.GET)
    public User getUserByname(String name){
        return userService.getUserByname(name);
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public User saveUser(User user){
        return userService.saveUser(user);
    }
}
