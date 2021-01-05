package com.sky.security.filter;

import com.sky.security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getAttribute("user");
        if(user == null){
            response.setContentType("text/plain");
            response.getWriter().write("need authentication");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        } else {
            String method = request.getMethod();
            if(!user.hasPermission(method)){
                response.setContentType("text/plain");
                response.getWriter().write("forbbiden");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }

        }
        return super.preHandle(request, response, handler);
    }

}
