package com.sky.security.dao;

import com.sky.security.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findByUserName(String userName);
}
