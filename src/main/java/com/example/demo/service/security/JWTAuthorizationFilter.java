package com.example.demo.service.security;

import com.example.demo.service.security.utils.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest Request, HttpServletResponse Response, FilterChain filterChain) throws ServletException, IOException {
        if(Request.getMethod().equalsIgnoreCase(SecurityConsts.OPTION_HTTP_METHOD)){
            Response.setStatus(SecurityConsts.OK.value());
        }else {
           String autorizationHeader =  Request.getHeader(HttpHeaders.AUTHORIZATION);
           if(autorizationHeader == null || autorizationHeader.startsWith(SecurityConsts.TOKEN_PREFIX)){
                filterChain.doFilter(Request,Response);
                return;
           }
        }
    }
}
