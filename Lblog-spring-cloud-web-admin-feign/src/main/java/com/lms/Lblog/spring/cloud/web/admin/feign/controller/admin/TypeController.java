package com.lms.Lblog.spring.cloud.web.admin.feign.controller.admin;


import com.lms.Lblog.spring.cloud.web.admin.feign.po.Type;
import com.lms.Lblog.spring.cloud.web.admin.feign.service.WebTypeService;
import com.lms.Lblog.spring.cloud.web.admin.feign.vo.MyPage;
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


/**
 * Autor Lms
 * Time 2019/9/20/020
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private WebTypeService typeService;

    @GetMapping("/types")       //查询，每页显示五条数据，  通过主键ID排序， 降序排序
    public String types(@PageableDefault(size=5,sort={"id"},direction = Sort.Direction.DESC) Pageable pageable,Model model){

        MyPage page=new MyPage();
        page.setPageNo(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        model.addAttribute("page",typeService.listType(page));
        return "admin/types";
    }

    @GetMapping("/types/input")     //接受新增分类点击请求
    public String input(Model model){

        model.addAttribute("type",new Type());
        return "admin/addType";
    }

    @GetMapping("/types/{id}/input")    //编辑分类,修改分类名称，先查询出分类信息在跳转到新增分类页面进行编辑
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/addType";
    }

    @PostMapping("/types")  //新增分类
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){  //@Valid用于后端校验

        Type type1=typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/addType";
        }

        Type t= typeService.saveType(type);
        if (t==null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";  //返回到/types映射的方法中查询，再返回页面 (默认使用get请求？)
    }


    @PostMapping("/types/{id}")  //修改分类
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){  //@Valid用于后端校验

        Type type1=typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/addType";
        }

        Type t= typeService.updateType(id,type);
        if (t==null){
            attributes.addFlashAttribute("message","修改失败");
        }else{
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";  //返回到/types映射的方法中查询，再返回页面 (默认使用get请求？)
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }


}
