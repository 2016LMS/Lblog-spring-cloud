package com.lms.Lblog.spring.cloud.web.admin.feign.service;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Blog;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.PageAndBlogQuery;
import javassist.NotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebBlogService {

    //按照id查找博客
    @RequestMapping(value = "/admin/blog",method = RequestMethod.GET)
    Blog getBlog(@RequestParam(value = "id") Long id);
    //按照id查找到博客，并将content转化为html格式
    @RequestMapping(value = "/admin/convertBlog",method = RequestMethod.GET)
    Blog getAndConvert(@RequestParam(value = "id") Long id) throws NotFoundException;//查询到博客，并将博客内容从markdown格式转换为html格式，因为新增博客保存的博客内容是markdown格式的

    //条件查询博客列表
    @RequestMapping(value = "/admin/blogsByQuery",method = RequestMethod.POST)
    MyPage<Blog> listBlog(PageAndBlogQuery pageAndBlogQuery);

    //分页查询博客
    @RequestMapping(value = "/admin/blogs",method = RequestMethod.POST)
    MyPage<Blog> listBlog(MyPage page);

    //根据标签查找博客并分页
    @RequestMapping(value = "/admin/blogsByTag",method = RequestMethod.POST)
    MyPage<Blog> listBlog(@RequestParam(value = "tagId") Long tagId, MyPage page);

    //推荐博客按阅读量排行
    @RequestMapping(value = "/admin/recommendBlogTop",method = RequestMethod.GET)
    List<Blog> listRecommendBlogTop(@RequestParam(value = "size")int size);

    //保存博客
    @RequestMapping(value = "/admin/blog", method = RequestMethod.POST)
    Blog saveBlog(Blog blog);

    //修改博客内容
    @RequestMapping(value = "/admin/blog" ,method = RequestMethod.PUT)
    Blog updateBlog(@RequestParam(value = "id")Long id, Blog blog) throws NotFoundException;

    //删除博客
    @RequestMapping(value = "/admin/blog",method = RequestMethod.DELETE)
    void deleteBlog(@RequestParam(value = "id") Long id);

    @RequestMapping(value = "/archives",method = RequestMethod.GET)
    Map<String,List<Blog>> arhciveBlog();

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    Long count();
}
