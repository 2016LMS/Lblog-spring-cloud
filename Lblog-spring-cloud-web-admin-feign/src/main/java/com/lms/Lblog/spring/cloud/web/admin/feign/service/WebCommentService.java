package com.lms.Lblog.spring.cloud.web.admin.feign.service;


import com.lms.Lblog.spring.cloud.web.admin.feign.po.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebCommentService {
    //当使用GET请求服务提供者时，基本数据类型参数不加@requestParam，则会被强制转化为POST请求，从而报错405
    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    List<Comment> listCommentByBlogId(@RequestParam(value = "blogId") Long blogId);

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    Comment saveComment(Comment comment);

}
