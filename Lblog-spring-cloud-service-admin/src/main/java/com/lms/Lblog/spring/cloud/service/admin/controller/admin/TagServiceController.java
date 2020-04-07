package com.lms.Lblog.spring.cloud.service.admin.controller.admin;

import com.lms.Lblog.spring.cloud.service.admin.po.Tag;
import com.lms.Lblog.spring.cloud.service.admin.service.TagService;
import com.lms.Lblog.spring.cloud.service.admin.vo.MyPage;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Autor Lms
 * Time 2020-3-20
 */
@Controller
@RequestMapping("/admin")
public class TagServiceController {

    @Autowired
    private TagService tagService;
    /**
      *给web层服务消费者的service层提供方法，也就是以前service层中的方法
      *@Date 14:17 2020-3-20
      *param [tag]
     **/

    //新增一个标签
    @PostMapping("/tag")
    public Tag saveTag(Tag tag){
        return tagService.saveTag(tag);
    }

    //根据id查询一个分类
    @GetMapping("/tag")
    @ResponseBody
    public Tag getTagById(Long id){
        return tagService.getTag(id);
    }

    //根据name查询一个分类
    @GetMapping("/tagByName")
    @ResponseBody
    public Tag getTagByName(String name){
        return tagService.getTagByName(name);
    }

    //根据分页信息查询一页的数据
    @PostMapping("/tags")
    @ResponseBody
    public MyPage<Tag> listTags(MyPage page){
        Pageable pageable =new PageRequest(page.getPageNo(),page.getPageSize());
        Page<Tag> dataPage=tagService.listTag(pageable);
        MyPage<Tag> myPage=new MyPage<>();
        myPage.setContent(dataPage.getContent());
        myPage.setPageNo(dataPage.getNumber());
        myPage.setPageSize(dataPage.getSize());
        myPage.setTotal(dataPage.getTotalElements());
        return myPage;
    }

    //查询所有的标签
    @GetMapping("/tags")
    @ResponseBody
    public List<Tag> listAll(){
        return tagService.listTag();
    }

    //修改分类信息
    @PutMapping("/tag")
    @ResponseBody
    //在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据(将java对象转换为json对象写入http response)
    public Tag updateType(Long id,Tag tag) throws NotFoundException {
        return tagService.updateTag(id,tag);
    }

    //删除一条记录
    @DeleteMapping("/tag")
    public void deleteTag(Long id){
        tagService.deleteTag(id);
    }

    //从阅读量由高到低查询分类
    @GetMapping("/tagsTop")
    @ResponseBody
    public List<Tag> listTagTop(Integer size){
        return tagService.listTagTop(size);
    }

    @PostMapping("/tagsByIds")
    public List<Tag> listTagsByIds(String ids){
        return tagService.listTag(ids);
    }
}
