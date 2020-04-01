package com.lms.Lblog.spring.cloud.service.admin.controller.admin;

import com.lms.Lblog.spring.cloud.service.admin.po.Blog;
import com.lms.Lblog.spring.cloud.service.admin.po.User;
import com.lms.Lblog.spring.cloud.service.admin.service.BlogService;
import com.lms.Lblog.spring.cloud.service.admin.service.TagService;
import com.lms.Lblog.spring.cloud.service.admin.service.TypeService;
import com.lms.Lblog.spring.cloud.service.admin.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.service.admin.vo.PageAndBlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


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

//    @GetMapping("/blogs")
//    public String blogs(@PageableDefault(size = 8,
//            sort= {"updateTime"},
//            direction = Sort.Direction.DESC) Pageable pageable,
//                        BlogQuery blog, Model model){
//
//        model.addAttribute("types",typeService.listType());
//        model.addAttribute("page",blogService.listBlog(pageable,blog));
//        return LIST;
//    }
//
//    @PostMapping("/blogs/search")
//    public String search(@PageableDefault(size = 8,sort= {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
//        model.addAttribute("page",blogService.listBlog(pageable,blog));
//        return "admin/adminBlogs :: blogList";
//    }
//
//    //跳转到新增博客页面
//    @GetMapping("blogs/input")
//    public String input(Model model){
//        model.addAttribute("types",typeService.listType());
//        model.addAttribute("tags",tagService.listTag());
//        model.addAttribute("blog",new Blog());
//        return INPUT;
//    }
//
//    //修改功能
//    @GetMapping("blogs/{id}/input")
//    public String editInput(@PathVariable Long id, Model model){
//        model.addAttribute("types",typeService.listType());
//        model.addAttribute("tags",tagService.listTag());
//        Blog blog=blogService.getBlog(id);
//        blog.init();
//        model.addAttribute("blog",blog);
//        return INPUT;
//    }
//
//    //新增博客
//    @PostMapping("/blogs")
//    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) throws NotFoundException {
//        blog.setUser((User) session.getAttribute("user"));
//        blog.setType(typeService.getType(blog.getType().getId()));
//        blog.setTags(tagService.listTag(blog.getTagIds()));
//
//        Blog b=null;
//        if (blog.getId()==null){
//            b=blogService.saveBlog(blog);
//        }else{
//            b=blogService.updateBlog(blog.getId(),blog);
//        }
//        if (b==null){
//            attributes.addFlashAttribute("message","操作失败");
//        }else{
//            attributes.addFlashAttribute("message","操作成功");
//        }
//        return REDIRECT_LIST;
//    }
//
//    @GetMapping("/blogs/{id}/delete")
//    public String delete(@PathVariable Long id,RedirectAttributes attributes){
//        blogService.deleteBlog(id);
//        attributes.addFlashAttribute("message","删除成功");
//        return REDIRECT_LIST;
//    }

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
    public Page<Blog> listBlog(PageAndBlogQuery pageAndBlogQuery){
        Pageable pageable =pageAndBlogQuery.getPageable();
        BlogQuery blog=pageAndBlogQuery.getBlogQuery();
        return blogService.listBlog(pageable,blog);
    }

    //分页查询博客
    @PostMapping("/blogs")
    @ResponseBody
    public Page<Blog> listBlog(Pageable pageable){
        return blogService.listBlog(pageable);
    }

    //根据标签查找博客并分页
    @PostMapping("/blogsByTag")
    @ResponseBody
    Page<Blog> listBlog(Long tagId, Pageable pageable){
        return blogService.listBlog(tagId, pageable);
    }

    //按照阅读量排行
    @GetMapping("/recommendBlogTop")
    @ResponseBody
    List<Blog> listRecommendBlogTop(Integer size){
        return blogService.listRecommendBlogTop(size);
    }

    //保存博客
    @PostMapping("/blog")
    @ResponseBody
    Blog saveBlog(Blog blog){
        return blogService.saveBlog(blog);
    }

    //修改博客内容
    @PutMapping("/blog")
    @ResponseBody
    Blog updateBlog(Long id, Blog blog) throws NotFoundException{
        return blogService.updateBlog(id,blog);
    }

    //删除博客
    @DeleteMapping("/blog")
    @ResponseBody
    void deleteBlog(Long id){
        blogService.deleteBlog(id);
    }
}

