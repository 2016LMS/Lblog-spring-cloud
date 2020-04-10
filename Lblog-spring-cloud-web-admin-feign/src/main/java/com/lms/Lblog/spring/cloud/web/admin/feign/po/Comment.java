package com.lms.Lblog.spring.cloud.web.admin.feign.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Autor Lms
 * Time 2019/8/29/029
 */
@Entity
@Table(name = "t_comment")
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private  String nickname;
    private  String email;
    private String content;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    //这个集合应该是自动产生的，通过map parentComment自动添加到list
    @JsonIgnore
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    //这个属性用来进行json序列化的，防止无限递归，因为replyComments属性加上@jsonIgnore注解后会不被序列化，导致回复评论不能传递回去
    //@Transient注解用于忽略hibernate的实体类映射
    @Transient
    private List<MyComment> myReplyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;

    public Comment(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public List<MyComment> getMyReplyComments() {
        return myReplyComments;
    }

    public void setMyReplyComments(List<MyComment> myReplyComments) {
        this.myReplyComments = myReplyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
