package com.lms.Lblog.spring.cloud.web.admin.feign.service;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Blog;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.PageAndBlogQuery;
import javassist.NotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebBlogService {

    //按照id查找博客
    @RequestMapping(value = "/admin/blog",method = RequestMethod.GET)
    Blog getBlog(Long id);
    //按照id查找到博客，并将content转化为html格式
    @RequestMapping(value = "/admin/convertBlog",method = RequestMethod.GET)
    Blog getAndConvert(Long id) throws NotFoundException;//查询到博客，并将博客内容从markdown格式转换为html格式，因为新增博客保存的博客内容是markdown格式的

    //条件查询博客列表
    @RequestMapping(value = "/admin/blogsByQuery",method = RequestMethod.POST)
    Page<Blog> listBlog(PageAndBlogQuery pageAndBlogQuery);

    //分页查询博客
    @RequestMapping(value = "/admin/blogs",method = RequestMethod.POST)
    Page<Blog> listBlog(Pageable pageable);

    //根据标签查找博客并分页
    @RequestMapping(value = "/admin/blogsByTag",method = RequestMethod.POST)
    Page<Blog> listBlog(@RequestParam(value = "tagId") Long tagId, Pageable pageable);

    //推荐博客按阅读量排行
    @RequestMapping(value = "/admin/recommendBlogTop",method = RequestMethod.GET)
    List<Blog> listRecommendBlogTop(Integer size);

    //保存博客
    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    Blog saveBlog(Blog blog);

    //修改博客内容
    @RequestMapping(value = "/blog" ,method = RequestMethod.PUT)
    Blog updateBlog(@RequestParam(value = "id")Long id, Blog blog) throws NotFoundException;

    //删除博客
    @RequestMapping(value = "/blog",method = RequestMethod.DELETE)
    void deleteBlog(Long id);
}
