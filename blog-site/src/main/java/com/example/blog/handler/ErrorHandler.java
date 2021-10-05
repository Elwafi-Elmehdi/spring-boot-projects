package com.example.blog.handler;

import com.example.blog.bean.HttpResponse;
import com.example.blog.consts.Error;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        String[] supportedMethods = exception.getSupportedMethods();
        HttpMethod supportedMethod = HttpMethod.resolve(supportedMethods[0]);
        return HttpResponse.createResponse(HttpStatus.METHOD_NOT_ALLOWED,String.format(Error.METHOD_IS_NOT_ALLOWED,supportedMethod));
    }

    @ExceptionHandler(RuntimeException.class)
    public  ResponseEntity<HttpResponse> error(){
        return HttpResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Error!");
    }
}
