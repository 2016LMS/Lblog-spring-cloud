package com.lms.Lblog.spring.cloud.web.admin.feign.controller.admin;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Blog;
import com.lms.Lblog.spring.cloud.web.admin.feign.po.Type;
import com.lms.Lblog.spring.cloud.web.admin.feign.po.User;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebBlogService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTagService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTypeService;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.PageAndBlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BlogController {

    private static String INPUT="admin/blogs-input";
    private static String LIST="admin/adminBlogs";
    private static String REDIRECT_LIST="redirect:/admin/blogs"; //重定向到这个mapping对应的controller

    @Autowired
    private WebBlogService blogService;

    @Autowired
    private WebTypeService typeService;

    @Autowired
    private WebTagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,
            sort= {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){

        model.addAttribute("types",typeService.listType());
        PageAndBlogQuery pageAndBlogQuery=new PageAndBlogQuery();
        //pageable类没有无参构造函数，所以不能使用feign传参，当服务提供者接受到这个参数进行jackson反序列时会解析失败
        pageAndBlogQuery.setPage(new MyPage(pageable.getPageNumber(),pageable.getPageSize()));
        pageAndBlogQuery.setBlogQuery(blog);
        model.addAttribute("page",blogService.listBlog(pageAndBlogQuery));
        return LIST;
    }

    @GetMapping("/test")
    @ResponseBody
    public List<Type> test(@PageableDefault(size = 8,
            sort= {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                           BlogQuery blog, Model model){
        return typeService.listType();
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort= {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        PageAndBlogQuery pageAndBlogQuery=new PageAndBlogQuery();
        pageAndBlogQuery.setPage(new MyPage(pageable.getPageNumber(),pageable.getPageSize()));
        pageAndBlogQuery.setBlogQuery(blog);
        model.addAttribute("page",blogService.listBlog(pageAndBlogQuery));
        return "admin/adminBlogs :: blogList";
    }

    //跳转到新增博客页面
    @GetMapping("blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    //修改功能
    @GetMapping("blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    //新增博客
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) throws NotFoundException {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b=null;
        if (blog.getId()==null){
            b=blogService.saveBlog(blog);
        }else{
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if (b==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}

