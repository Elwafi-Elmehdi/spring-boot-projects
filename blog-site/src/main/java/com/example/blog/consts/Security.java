package com.example.blog.consts;

import org.springframework.http.HttpStatus;

public class Security {
    public static final long EXPIRATION_TIME = 432_000_000; // JWT 5 Days
    public static final HttpStatus OK = HttpStatus.OK;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String AUTHORITIES = "authorities";
    public static final String OPTION_HTTP_METHOD = "OPTION";
    public static final String[] PUBLIC_URLS = {"/users/login","/users/create","/users/all","/categories/**","/comments/**"};
    public static final String COMPANY_LLC = "BLog Mehdi";
    public static final String COMPANY_LLC_ADMINISTRATON = "BLog Mehdi administration";
    public static final String timeZone = "Morocco/Casablanca";
}
