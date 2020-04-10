package com.lms.Lblog.spring.cloud.service.admin.service.impl;

import com.lms.Lblog.spring.cloud.service.admin.dao.BlogRepository;
import com.lms.Lblog.spring.cloud.service.admin.po.Blog;
import com.lms.Lblog.spring.cloud.service.admin.po.Type;
import com.lms.Lblog.spring.cloud.service.admin.service.BlogService;
import com.lms.Lblog.spring.cloud.service.admin.utility.MarkdownUtils;
import com.lms.Lblog.spring.cloud.service.admin.utility.MyBeanUtils;
import com.lms.Lblog.spring.cloud.service.admin.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Autor Lms
 * Time 2019/9/26/026
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Blog getAndConvert(Long id) throws NotFoundException {
        Blog blog=blogRepository.getOne(id);
        if(blog == null){
            throw new NotFoundException("博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        String content2= MarkdownUtils.markdownToHtmlExtensions(content);
        b.setContent(content2);
        return b;
    }

    //按条件搜索博客
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }




    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable=new PageRequest(0,size,sort);
        List<Blog> ss=blogRepository.findTop(pageable);
        System.out.println(ss.size());
        return ss;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0); //设置新增博客的浏览次数为0；
        }else{
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) throws NotFoundException {
        Blog b=blogRepository.getOne(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));//把blog中不为空的属性复制到b中
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Map<String, List<Blog>> arhciveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long count() {
        return blogRepository.count();
    }
}
