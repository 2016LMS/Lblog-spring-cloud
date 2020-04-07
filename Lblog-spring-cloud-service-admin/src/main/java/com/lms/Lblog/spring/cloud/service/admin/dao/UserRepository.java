package com.lms.Lblog.spring.cloud.service.admin.dao;

import com.lms.Lblog.spring.cloud.service.admin.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Autor Lms
 * Time 2019/9/1/001
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
