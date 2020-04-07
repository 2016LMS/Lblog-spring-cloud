package com.lms.Lblog.spring.cloud.web.admin.feign.service;


import com.lms.Lblog.spring.cloud.web.admin.feign.po.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebCommentService {
    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    List<Comment> listCommentByBlogId(Long blogId);

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    Comment saveComment(Comment comment);

}
