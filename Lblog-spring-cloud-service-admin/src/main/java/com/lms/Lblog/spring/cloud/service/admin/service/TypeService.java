package com.lms.Lblog.spring.cloud.service.admin.service;

import com.lms.Lblog.spring.cloud.service.admin.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Autor Lms
 * Time 2019/9/20/020
 */
public interface TypeService {

    Type saveType(Type type);   //保存分类

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
