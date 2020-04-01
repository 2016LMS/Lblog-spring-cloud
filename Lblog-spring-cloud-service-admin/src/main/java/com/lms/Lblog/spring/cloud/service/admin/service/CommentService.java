package com.lms.Lblog.spring.cloud.service.admin.service;



import com.lms.Lblog.spring.cloud.service.admin.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

}
