package com.example.demo.service.security;

import org.springframework.http.HttpStatus;

public class SecurityConsts {
    public static final long EXPIRATION_TIME = 432_000_000; // JWT 5 Days
    public static final HttpStatus OK = HttpStatus.OK;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to acess this page";
    public static final String OPTION_HTTP_METHOD = "OPTION";
    public static final String[] PUBLIC_URLS = {"/swagger-ui**","/user/login/**","/user/register/**","/user/resetpassword/**","/user/image/**"};
    public static final String COMPANY_LLC = "Company LLC";
    public static final String COMPANY_LLC_ADMINISTRATON = "Company LLC administration";
    public static final String timeZone = "Morocco/Casablanca";
}
