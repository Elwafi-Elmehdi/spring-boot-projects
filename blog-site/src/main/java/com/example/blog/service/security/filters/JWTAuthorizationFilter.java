package com.example.blog.service.security.filters;

import com.example.blog.consts.Error;
import com.example.blog.consts.Security;
import com.example.blog.service.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest Request, HttpServletResponse Response, FilterChain filterChain) throws ServletException, IOException {
        if(Request.getMethod().equalsIgnoreCase(Security.OPTION_HTTP_METHOD)){
            Response.setStatus(Security.OK.value());
        }else {
            String autorizationHeader =  Request.getHeader(HttpHeaders.AUTHORIZATION);
            if(autorizationHeader == null || !autorizationHeader.startsWith(Security.TOKEN_PREFIX)){
                filterChain.doFilter(Request,Response);
                return;
            }
            String token = autorizationHeader.substring(Security.TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token);

            if(jwtTokenProvider.isTokenValid(username,token) && SecurityContextHolder.getContext().getAuthentication() == null){

                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username,authorities,Request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                SecurityContextHolder.clearContext();
            }

        }
        filterChain.doFilter(Request,Response);
    }
}
