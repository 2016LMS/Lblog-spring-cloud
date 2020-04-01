package com.lms.Lblog.spring.cloud.service.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Autor Lms
 * Time 2020-3-15
 */
@Controller
public class AdminController {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "hi",method = RequestMethod.GET)
    public String SayHi(String message){
//        return String.format("Hi your message is : %s port: %s ",message,port);
//        return new User("user'name","22years");
//        Comment comment=new Comment();
        return "redirect:/html1";
    }

    @RequestMapping("/html1")
    @ResponseBody
    public String html(){
        return "html1";
    }
}
