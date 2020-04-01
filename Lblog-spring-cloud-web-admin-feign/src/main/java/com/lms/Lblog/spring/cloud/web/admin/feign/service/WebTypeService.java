package com.lms.Lblog.spring.cloud.web.admin.feign.service;

import com.lms.Lblog.spring.cloud.web.admin.feign.po.Type;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "Lblog-spring-cloud-service-admin")
public interface WebTypeService {
    //保存分类
    @RequestMapping(value = "/admin/type",method = RequestMethod.POST)
    Type saveType(Type type);
    //按id查询分类
    @RequestMapping(value = "/admin/types",method=RequestMethod.GET)
    Type getType(@RequestParam(value = "id") Long id);

    //根据name查询分类
    @RequestMapping(value = "/admin/typeByName",method = RequestMethod.GET)
    Type getTypeByName(@RequestParam(value = "name") String name);

    //根据分页信息查询一页的记录
    @RequestMapping(value = "/admin/types",method = RequestMethod.POST)
    Page<Type> listType(Pageable pageable);


    //修改分类
    @RequestMapping(value = "/admin/type",method = RequestMethod.PUT)
    Type updateType(@RequestParam(value = "id") Long id,Type type);

    //删除分类
    @RequestMapping(value = "/admin/type",method = RequestMethod.DELETE)
    void deleteType(@RequestParam(value = "id") Long id);

    //查询到所有的分类
    @RequestMapping(value = "/admin/types",method = RequestMethod.GET )
    List<Type> listType();

    //按阅读量排行查询
    @RequestMapping(value = "/admin/typesTop",method = RequestMethod.GET)
    List<Type> listTypeTop(@RequestParam(value = "size") Integer size);


}
