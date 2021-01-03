package com.sky.security.service;

import com.sky.security.model.UserDTO;
import com.sky.security.model.UserVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService extends BeanCopyService {

    UserVO create(UserDTO user);

    UserVO update(@RequestBody UserDTO user);

    void delete(@PathVariable Long id);

    UserVO get(@PathVariable Long id);

    List<UserVO> queryJpa(String name);
}
