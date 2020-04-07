package com.lms.Lblog.spring.cloud.service.admin.controller.admin;

import com.lms.Lblog.spring.cloud.service.admin.po.Type;
import com.lms.Lblog.spring.cloud.service.admin.service.TypeService;
import com.lms.Lblog.spring.cloud.service.admin.vo.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Autor Lms
 * Time 2020-3-20
 */
@Controller
@RequestMapping("/admin")
public class TypeServiceController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/serviceTest")
    @ResponseBody
    public List<String> test(){
        List<String> list=new ArrayList<>();
        list.add("第一条数据");
        list.add("第二条数据tt");
        return list;
    }

    //保存分类
    @PostMapping("/type")
    @ResponseBody
    public Type saveType(@RequestBody  Type type){
        return typeService.saveType(type);
    }

    //根据id查询一个分类
    @GetMapping("/type")
    @ResponseBody
    public Type getTypeById(Long id){
        return typeService.getType(id);
    }

    //根据name查询一个分类
    @GetMapping("/typeByName")
    @ResponseBody
    public Type getTypeByName(String name){
        return typeService.getTypeByName(name);
    }

    //根据分页信息查询一页的数据
    @PostMapping("/types")
    @ResponseBody
    //feign对应的服务实现上的参数@RequestBody 注解必不可少，否则接受到参数可能为null
    public MyPage<Type> listTypes(@RequestBody MyPage page){
        Pageable pageable =new PageRequest(page.getPageNo(),page.getPageSize());
        Page<Type> dataPage=typeService.listType(pageable);
        MyPage<Type> myPage=new MyPage<>();
        myPage.setContent(dataPage.getContent());
        myPage.setPageNo(dataPage.getNumber());
        myPage.setPageSize(dataPage.getSize());
        myPage.setTotal(dataPage.getTotalElements());
        return myPage;
    }

    //修改分类信息
    @PutMapping("/type")
    public Type updateType(Long id,Type type){
        return typeService.updateType(id,type);
    }

    //删除一条记录
    @DeleteMapping("/type")
    public void deleteType(Long id){
        typeService.deleteType(id);
    }

    //查询所有的分类
    @GetMapping("/types")
    @ResponseBody
    public List<Type> listAll(){
        return typeService.listType();
    }

    //从阅读量由高到低查询分类
    @GetMapping("/typesTop")
    @ResponseBody
    public List<Type> listTypeTop(Integer size){
        return typeService.listTypeTop(size);
    }
}
