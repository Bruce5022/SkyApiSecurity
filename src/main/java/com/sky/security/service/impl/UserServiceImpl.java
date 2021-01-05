package com.sky.security.service.impl;

import com.lambdaworks.crypto.SCryptUtil;
import com.sky.security.dao.UserRepository;
import com.sky.security.model.User;
import com.sky.security.model.UserDTO;
import com.sky.security.model.UserVO;
import com.sky.security.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserVO create(UserDTO user) {
        User copyBean = this.copyBean(user, User.class);
        copyBean.setPwd(SCryptUtil.scrypt(copyBean.getPwd(),32768,8,1));
        User savedUser = userRepository.save(copyBean);
        return this.copyBean(savedUser, UserVO.class);
    }

    @Override
    public UserVO update(UserDTO user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserVO get(Long id) {
        Optional<User> user = userRepository.findById(id);
        return this.copyBean(user.get(), UserVO.class);
    }

    @Override
    public List<UserVO> queryJpa(String name) {
        List<User> userList = userRepository.findByName(name);
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @Override
    public UserVO login(UserDTO user) {
        return null;
    }
}
