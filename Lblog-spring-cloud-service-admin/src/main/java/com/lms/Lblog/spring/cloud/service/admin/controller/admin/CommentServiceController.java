package com.lms.Lblog.spring.cloud.service.admin.controller.admin;

import com.lms.Lblog.spring.cloud.service.admin.po.Comment;
import com.lms.Lblog.spring.cloud.service.admin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Autor Lms
 * Time 2020-4-2
 */
@RestController
public class CommentServiceController {

    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    List<Comment> listCommentByBlogId(Long blogId){
        return commentService.listCommentByBlogId(blogId);
    }

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    Comment saveComment(Comment comment){
        return commentService.saveComment(comment);
    }
}
