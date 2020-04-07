package com.lms.Lblog.spring.cloud.web.admin.feign.controller.admin;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Type;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebBlogService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTypeService;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.PageAndBlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Autor Lms
 * Time 2020-3-13
 */
@Controller
public class TypeShowController {

    @Autowired
    private WebTypeService typeService;

    @Autowired
    private WebBlogService blogService;

    @GetMapping("/types/{id}")
    public String Types(@PageableDefault(size = 50,
            sort= {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model, @PathVariable Long id){
        List<Type> types=typeService.listTypeTop(100);
        if(id==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        PageAndBlogQuery pageAndBlogQuery= new PageAndBlogQuery();
        pageAndBlogQuery.setBlogQuery(blogQuery);
        pageAndBlogQuery.setPage(new MyPage(pageable.getPageNumber(),pageable.getPageSize()));
        model.addAttribute("page",blogService.listBlog(pageAndBlogQuery));
        model.addAttribute("activeTypeId",id);
        return "blogType";
    }
}
