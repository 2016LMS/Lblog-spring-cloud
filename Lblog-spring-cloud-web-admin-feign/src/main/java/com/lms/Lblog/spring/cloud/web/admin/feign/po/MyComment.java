package com.lms.Lblog.spring.cloud.web.admin.feign.po;

import java.util.Date;

/**
 * Autor Lms
 * Time 2020-4-9
 */
public class MyComment {
    private Long id;
    private  String nickname;
//    private  String email;
    private String content;
    private String avatar;
//    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String parentCommentName;

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

    public String getParentCommentName() {
        return parentCommentName;
    }

    public void setParentCommentName(String parentCommentName) {
        this.parentCommentName = parentCommentName;
    }
}
