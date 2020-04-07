package com.lms.Lblog.spring.cloud.service.admin.controller.admin;

import com.lms.Lblog.spring.cloud.service.admin.po.Blog;
import com.lms.Lblog.spring.cloud.service.admin.service.BlogService;
import com.lms.Lblog.spring.cloud.service.admin.service.TagService;
import com.lms.Lblog.spring.cloud.service.admin.service.TypeService;
import com.lms.Lblog.spring.cloud.service.admin.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.service.admin.vo.MyPage;
import com.lms.Lblog.spring.cloud.service.admin.vo.PageAndBlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * Autor Lms
 * Time 2019/9/15/015
 */
@Controller
@RequestMapping("/admin")
public class BlogServiceController {

    private static String INPUT="admin/blogs-input";
    private static String LIST="admin/adminBlogs";
    private static String REDIRECT_LIST="redirect:/admin/blogs"; //重定向到这个mapping对应的controller

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    //改造为微服务新增的方法

    //按照id查找博客
    @GetMapping("/blog")
    @ResponseBody
    public Blog getBlog(Long id){
        return blogService.getBlog(id);
    }

    //按照id查找到博客，并将content转化为html格式
    @GetMapping("/convertBlog")
    @ResponseBody
    public Blog getAndConvert(Long id) throws NotFoundException {
        return blogService.getAndConvert(id);
    }
    //条件查询博客列表
    @PostMapping("/blogsByQuery")
    @ResponseBody
    public MyPage<Blog> listBlog(@RequestBody PageAndBlogQuery pageAndBlogQuery){
        int pageNum=pageAndBlogQuery.getPage().getPageNo();
        int pageSize=pageAndBlogQuery.getPage().getPageSize();
        Pageable pageable =new PageRequest(pageNum,pageSize);
        BlogQuery blog=pageAndBlogQuery.getBlogQuery();
        Page<Blog> dataPage = blogService.listBlog(pageable,blog);
        MyPage<Blog> myPage=new MyPage<>();
        myPage.setContent(dataPage.getContent());
        myPage.setPageNo(dataPage.getNumber());
        myPage.setPageSize(dataPage.getSize());
        myPage.setTotal(dataPage.getTotalElements());
        return myPage;
    }

    //分页查询博客
    @PostMapping("/blogs")
    @ResponseBody
    public MyPage<Blog> listBlog(@RequestBody MyPage myPage){
        Pageable pageable=new PageRequest(myPage.getPageNo(),myPage.getPageSize());
        Page<Blog> dataPage = blogService.listBlog(pageable);
        MyPage<Blog> myPageResult=new MyPage<>();
        myPageResult.setContent(dataPage.getContent());
        myPageResult.setPageNo(dataPage.getNumber());
        myPageResult.setPageSize(dataPage.getSize());
        myPageResult.setTotal(dataPage.getTotalElements());
        return myPageResult;
    }

    //根据标签查找博客并分页
    @PostMapping("/blogsByTag")
    @ResponseBody
    public MyPage<Blog> listBlog(Long tagId, MyPage myPage){
        Pageable pageable=new PageRequest(myPage.getPageNo(),myPage.getPageSize());
        Page<Blog> dataPage = blogService.listBlog(tagId, pageable);
        MyPage<Blog> myPageResult=new MyPage<>();
        myPageResult.setContent(dataPage.getContent());
        myPageResult.setPageNo(dataPage.getNumber());
        myPageResult.setPageSize(dataPage.getSize());
        myPageResult.setTotal(dataPage.getTotalElements());
        return myPageResult;
    }

    //按照阅读量排行
    @GetMapping("/recommendBlogTop")
    @ResponseBody
    public List<Blog> listRecommendBlogTop(Integer size){
        return blogService.listRecommendBlogTop(size);
    }

    //保存博客
    @PostMapping("/blog")
    @ResponseBody
    public Blog saveBlog(@RequestBody Blog blog){
        return blogService.saveBlog(blog);
    }

    //修改博客内容
    @PutMapping("/blog")
    @ResponseBody
    public Blog updateBlog(Long id, @RequestBody Blog blog) throws NotFoundException{
        return blogService.updateBlog(id,blog);
    }

    //删除博客
    @DeleteMapping("/blog")
    @ResponseBody
    public void deleteBlog(Long id){
        blogService.deleteBlog(id);
    }

    //博客归档
    @GetMapping("/archives")
    @ResponseBody
    public Map<String,List<Blog>> arhciveBlog(){
        return blogService.arhciveBlog();
    }

    @GetMapping("/count")
    @ResponseBody
    public Long count(){
        return blogService.count();
    }
}

