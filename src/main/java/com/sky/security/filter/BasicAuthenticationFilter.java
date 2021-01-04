package com.sky.security.filter;

import com.lambdaworks.crypto.SCryptUtil;
import com.sky.security.dao.UserRepository;
import com.sky.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Order(2)
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {

            String[] basics = authorization.split("Basic ");
            String token64 = basics[1];
            String token = new String(Base64Utils.decodeFromString(token64));
            String[] items = token.split(":");
            String userName = items[0];
            String pwd = items[1];
            List<User> userList = userRepository.findByUserName(userName);

            for (User user : userList) {
                String userPwd = user.getPwd();
                if (!StringUtils.isEmpty(userPwd) && SCryptUtil.check(pwd,userPwd)) {
                    httpServletRequest.setAttribute("user", user);
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
