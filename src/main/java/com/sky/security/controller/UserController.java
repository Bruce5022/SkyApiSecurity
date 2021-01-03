package com.sky.security.controller;

import com.sky.security.model.User;
import com.sky.security.model.UserDTO;
import com.sky.security.model.UserVO;
import com.sky.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    @PostMapping
    public UserVO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserVO update(@RequestBody UserDTO user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserVO get(@PathVariable Long id, HttpServletRequest servletRequest) {
        User user = (User) servletRequest.getAttribute("user");
        if (user == null || !user.getId().equals(id)) {
            throw new RuntimeException("身份认证信息异常，获取用户信息失败");
        }
        return userService.get(id);
    }

//    @GetMapping
//    public List<Map<String, Object>> query(String name) {
//        String sql = "select * from user where name ='" + name + "'";
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
//        return maps;
//    }

    @GetMapping
    public List<UserVO> queryJpa(String name) {
        return userService.queryJpa(name);
    }
}
