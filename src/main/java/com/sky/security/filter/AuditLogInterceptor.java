package com.sky.security.filter;

import com.sky.security.dao.AuditLogRepository;
import com.sky.security.model.AuditLog;
import com.sky.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuditLog log = new AuditLog();
        log.setMethod(request.getMethod());
        log.setPath(request.getRequestURI());

        User user = (User) request.getAttribute("user");
        if(user != null){
            log.setUserName(user.getUserName());
        }

        auditLogRepository.save(log);
        request.setAttribute("auditLogId",log.getId());

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        AuditLog auditLog = auditLogRepository.findById(auditLogId).get();
        auditLog.setStatus(response.getStatus());
        auditLogRepository.save(auditLog);
        super.afterCompletion(request, response, handler, ex);
    }
}
