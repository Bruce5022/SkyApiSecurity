package com.sky.security.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    // 每秒钟只有一个请求过去
    private RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
            writer.flush();
            return;
        }
    }
}
