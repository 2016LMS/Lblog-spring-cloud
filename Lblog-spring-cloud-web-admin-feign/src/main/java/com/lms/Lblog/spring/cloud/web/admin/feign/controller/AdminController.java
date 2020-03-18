package com.lms.Lblog.spring.cloud.web.admin.feign.controller;

import com.lms.Lblog.spring.cloud.web.admin.feign.service.AdminService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Autor Lms
 * Time 2020-3-15
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "hi",method = RequestMethod.GET)
    public String sayHi(String name,String age){

        return adminService.sayHi(name,age);
    }

    @RequestMapping("/html")
    public String jumpToPage(){
        return "html";
    }
}
