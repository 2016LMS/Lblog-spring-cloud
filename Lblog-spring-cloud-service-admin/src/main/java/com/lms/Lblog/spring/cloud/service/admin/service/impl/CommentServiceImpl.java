package com.lms.Lblog.spring.cloud.service.admin.service.impl;

import com.lms.Lblog.spring.cloud.service.admin.dao.CommentRepository;
import com.lms.Lblog.spring.cloud.service.admin.po.Comment;
import com.lms.Lblog.spring.cloud.service.admin.po.MyComment;
import com.lms.Lblog.spring.cloud.service.admin.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Autor Lms
 * Time 2020-3-2
 * @Service注释都是在impl类中的
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Value("${reply.avatar}")
    private String replyAvatar;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId!=-1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments(查询出来的顶级评论集合)
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        //将元数据拷贝一份出来操作，防止修改数据库
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中，每一个顶级评论都有一个子集合
        combineChildren(commentsView);

        for (int i=0;i<commentsView.size();i++){
            List<Comment> list = commentsView.get(i).getReplyComments();
            List<MyComment> mylist = commentsView.get(i).getMyReplyComments();
            MyComment myComment=null;
            for (int j=0;j<list.size();j++){
                //将comment实体中的replyComments属性赋值到MyReplyComment属性中
                myComment=new MyComment();
                myComment.setId(list.get(j).getId());
                myComment.setAvatar(replyAvatar);
                myComment.setCreateTime(list.get(i).getCreateTime());
                myComment.setContent(list.get(i).getContent());
                myComment.setParentCommentName(list.get(i).getParentComment().getNickname());
                commentsView.get(i).getMyReplyComments().add(myComment);
            }
        }

        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //临时存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
