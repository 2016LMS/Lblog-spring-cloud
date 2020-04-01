package com.lms.Lblog.spring.cloud.service.admin.vo;

import org.springframework.data.domain.Pageable;

/**
 * Autor Lms
 * Time 2020-3-29
 */

public class PageAndBlogQuery {
    private Pageable pageable;
    private BlogQuery blogQuery;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public BlogQuery getBlogQuery() {
        return blogQuery;
    }

    public void setBlogQuery(BlogQuery blogQuery) {
        this.blogQuery = blogQuery;
    }
}
