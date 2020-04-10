package com.lms.Lblog.spring.cloud.web.admin.feign.controller;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Comment;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebBlogService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Autor Lms
 * Time 2020-3-2
 */
@Controller
public class CommentController {

    @Autowired
    private WebCommentService commentService;

    @Autowired
    private WebBlogService blogService;

    //在配置文件application.properties中设置的全局变量
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String listComments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blogDetail :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/"+ comment.getBlog().getId();//重定向到上一个controller中
    }
}
