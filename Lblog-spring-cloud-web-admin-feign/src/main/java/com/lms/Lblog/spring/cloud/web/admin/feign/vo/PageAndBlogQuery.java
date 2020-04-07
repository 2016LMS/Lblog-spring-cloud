package com.lms.Lblog.spring.cloud.web.admin.feign.vo;


/**
 * Autor Lms
 * Time 2020-3-29
 */

public class PageAndBlogQuery {
    private BlogQuery blogQuery;
    private MyPage mypage;


    public PageAndBlogQuery() {

    }

    public BlogQuery getBlogQuery() {
        return blogQuery;
    }

    public void setBlogQuery(BlogQuery blogQuery) {
        this.blogQuery = blogQuery;
    }

    public MyPage getPage() {
        return mypage;
    }

    public void setPage(MyPage page) {
        this.mypage = page;
    }
}


