package com.lms.Lblog.spring.cloud.service.admin.dao;

import com.lms.Lblog.spring.cloud.service.admin.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //只查询父评论为空的评论即一级评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
