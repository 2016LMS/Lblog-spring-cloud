package com.lms.Lblog.spring.cloud.service.admin.dao;

import com.lms.Lblog.spring.cloud.service.admin.po.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Autor Lms
 * Time 2019/9/26/026
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //找出博客的年份
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year DESC")
    List<String> findGroupYear();
    //按年份找出该年份的博客
    @Query("select b from Blog b where function('data_format',b.updateTime,'%Y') = ?1 ")
    List<Blog> findByYear(String year);
}
