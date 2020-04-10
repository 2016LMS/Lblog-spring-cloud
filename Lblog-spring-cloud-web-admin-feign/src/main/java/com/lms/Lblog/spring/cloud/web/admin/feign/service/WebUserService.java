package com.lms.Lblog.spring.cloud.web.admin.feign.service;


import com.lms.Lblog.spring.cloud.web.admin.feign.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Autor Lms
 * Time 2019/9/1/001
 */
@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebUserService {
    @RequestMapping(value = "/loginCheck",method = RequestMethod.POST)
    User checkUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);

    @RequestMapping(value = "/userByName",method = RequestMethod.GET)
    User getUserByname(@RequestParam(value = "username") String name);

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    User saveUser(User user);
}
