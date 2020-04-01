package com.lms.Lblog.spring.cloud.service.admin.service;


import com.lms.Lblog.spring.cloud.service.admin.po.Tag;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {

    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

   List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag type) throws NotFoundException;

    void deleteTag(Long id);
}
