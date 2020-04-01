package com.lms.Lblog.spring.cloud.service.admin.service.impl;

import com.lms.Lblog.spring.cloud.service.admin.dao.TypeRepository;
import com.lms.Lblog.spring.cloud.service.admin.po.Type;
import com.lms.Lblog.spring.cloud.service.admin.service.TypeService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Autor Lms
 * Time 2019/9/20/020
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);

    }

    @Transactional          //声明成事务，事务的四大特性，原子性，隔离性，一致性，持久性
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);   //思考为什么findOne（id）不行？
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {     //分页查询
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {        //更新分类，修改
        Type t=typeRepository.getOne(id);
        if (t==null){
            try {
                throw new NotFoundException("不存在该分类");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(type,t);       //springframework包下的BeanUtils是将type的各个属性赋值给t

        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {

        typeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort= new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);
    }
}
