package com.lms.Lblog.spring.cloud.web.admin.feign.controller.admin;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Tag;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebBlogService;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTagService;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.BlogQuery;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
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
public class TagShowController {

    @Autowired
    private WebTagService tagService;

    @Autowired
    private WebBlogService blogService;

    @GetMapping("/tags/{id}")
    public String Tags(@PageableDefault(size = 50,
            sort= {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model, @PathVariable Long id){
        List<Tag> tags=tagService.listTagTop(100);
        if(id==-1){
            id=tags.get(0).getId();
        }
        MyPage page=new MyPage();
        page.setPageNo(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,page));
        model.addAttribute("activeTagId",id);
        return "blogTag";
    }
}
