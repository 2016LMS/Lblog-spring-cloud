package com.lms.Lblog.spring.cloud.web.admin.feign.service;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Tag;
import javassist.NotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebTagService {

    //保存标签
    @RequestMapping(value = "/admin/tags",method = RequestMethod.POST)
    Tag saveTag(Tag tag);

    //按id查询标签
    @RequestMapping(value = "/admin/tag",method=RequestMethod.GET)
    Tag getTag(@RequestParam(value = "id") Long id);

    //根据name查询标签
    @RequestMapping(value = "/admin/tagByName",method = RequestMethod.GET)
    Tag getTagByName(@RequestParam(value = "name") String name);

    //根据分页信息查询一页的记录
    @RequestMapping(value = "/admin/tags",method = RequestMethod.POST)
    Page<Tag> listTag(Pageable pageable);

    //查询到所有的标签
    @RequestMapping(value = "/admin/tags",method = RequestMethod.GET )
    List<Tag> listTag();

    //按阅读量排行查询
    @RequestMapping(value = "/admin/tagsTop",method = RequestMethod.GET)
    List<Tag> listTagTop(@RequestParam(value = "size") Integer size);

    //根据多个ID组成的字符串查找tag集合
    @RequestMapping(value = "/admin/tagsByIds",method = RequestMethod.POST)
    List<Tag> listTag(@RequestParam(value = "ids") String ids);

    //修改标签
    @RequestMapping(value = "/admin/tag",method = RequestMethod.POST)
    Tag updateTag(@RequestParam(value = "id") Long id, Tag tag) throws NotFoundException;

    //删除标签
    @RequestMapping(value = "/admin/tag",method = RequestMethod.DELETE)
    void deleteTag(@RequestParam(value = "id") Long id);
}
