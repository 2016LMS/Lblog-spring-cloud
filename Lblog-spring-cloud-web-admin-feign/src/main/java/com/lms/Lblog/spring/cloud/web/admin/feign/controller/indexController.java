package com.lms.Lblog.spring.cloud.web.admin.feign.controller;
/*
 *Autor Lms
 *Time 2019/8/25/025 12:13
 */

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Blog;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebBlogService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebCommentService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTagService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTypeService;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @Autowired
    private WebBlogService blogService;

    @Autowired
    private WebTypeService typeService;

    @Autowired
    private WebTagService tagService;

    @Autowired
    private WebCommentService commentService;

    //进入首页
    @RequestMapping("/")
    public String index(@PageableDefault(size = 8,
            sort= {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){

        MyPage page=new MyPage(pageable.getPageNumber(),pageable.getPageSize());
        model.addAttribute("page",blogService.listBlog(page));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(4));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
//        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";   //“/admin”就是controller的映射，“admin/login”就是admin文件夹下的login.html动态页面
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) throws NotFoundException {
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        Blog b=blogService.getBlog(id);
        b.setViews(b.getViews()+1);
        blogService.updateBlog(b.getId(),b);
        return "blogDetail";
    }

    @GetMapping("/aboutMe")
    public String aboutMe(){
        return "aboutMe";
    }
}
