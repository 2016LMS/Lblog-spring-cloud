package com.lms.Lblog.spring.cloud.web.admin.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//因为feign客户端也就是本项目是注册在eureka服务器中的，二调用的服务Lblog-spring-cloud-service-admin也是注册在eureka服务器中的，所以通过value属性指定服务名称就可以直接调用到这个远程服务
@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface AdminService {
    @RequestMapping(value = "hi",method= RequestMethod.GET)
    public String sayHi(@RequestParam(value = "name") String name,@RequestParam(value = "age") String age);
}
