package com.example.blog.handler;

import com.example.blog.bean.HttpResponse;
import com.example.blog.consts.Error;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException(){
        return HttpResponse.createResponse(HttpStatus.BAD_REQUEST,Error.ACCOUNT_DISABLED);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(){
        return HttpResponse.createResponse(HttpStatus.BAD_REQUEST,Error.INCORRECT_CREDENTIALS);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<HttpResponse> accessDeniedException(){
        return HttpResponse.createResponse(HttpStatus.FORBIDDEN,Error.NO_PERMISSIONS);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSuppportedException(HttpRequestMethodNotSupportedException exception){
        String[] supportedMethods = exception.getSupportedMethods();
        HttpMethod supportedMethod = HttpMethod.resolve(supportedMethods[0]);
        return HttpResponse.createResponse(HttpStatus.METHOD_NOT_ALLOWED,String.format(Error.METHOD_IS_NOT_ALLOWED,supportedMethod));
    }
}
