package com.lms.Lblog.spring.cloud.web.admin.feign.controller.admin;

/**
 * Autor Lms
 * Time 2020-3-10
 */

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Tag;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTagService;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private WebTagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        MyPage page=new MyPage();
        page.setPageNo(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        model.addAttribute("page",tagService.listTag(page));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag,BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) throws NotFoundException {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

    //改造成单体应用时新增的方法

//    //新增一个标签
//    @PostMapping("/tag")
//    public Tag saveTag(Tag tag){
//        return tagService.saveTag(tag);
//    }
//
//    //根据id查询一个分类
//    @GetMapping("/type")
//    public Tag getTypeById(Long id){
//        return tagService.getTag(id);
//    }
//
//    //根据name查询一个分类
//    @GetMapping("/typeByName")
//    @ResponseBody
//    public Tag getTypeByName(String name){
//        return tagService.getTagByName(name);
//    }
//
//    //根据分页信息查询一页的数据
//    @PostMapping("/tags")
//    @ResponseBody
//    public Page<Tag> listTypes(Pageable pageable){
//        return tagService.listTag(pageable);
//    }
//
//    //查询所有的标签
//    @GetMapping("/tags")
//    public List<Tag>listAll(){
//        return tagService.listTag();
//    }
//
//    //修改分类信息
//    @PostMapping("/type")
//    public Tag updateType(Long id,Tag tag) throws NotFoundException {
//        return tagService.updateTag(id,tag);
//    }
//
//    //删除一条记录
//    public void deleteTag(Long id){
//        tagService.deleteTag(id);
//    }
//
//    //从阅读量由高到低查询分类
//    @GetMapping("/typesTop")
//    @ResponseBody
//    public List<Tag> listTypeTop(Integer size){
//        return tagService.listTagTop(size);
//    }
//
//    @PostMapping("/tagsByIds")
//    public List<Tag> listTagsByIds(String ids){
//        return tagService.listTag(ids);
//    }


}

