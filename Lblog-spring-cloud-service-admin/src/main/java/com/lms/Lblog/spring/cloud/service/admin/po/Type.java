package com.lms.Lblog.spring.cloud.service.admin.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Autor Lms
 * Time 2019/8/29/029
 */
@Entity
@Table(name = "t_type")
public class Type implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能填为空")
    private String name;

    //@JsonIgnore注解防止出现Infinite recursion无限递归
    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
