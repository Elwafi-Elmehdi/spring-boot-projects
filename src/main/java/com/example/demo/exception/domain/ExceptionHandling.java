package com.example.demo.exception.domain;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.bean.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandling  {
    private  final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final String ACCOUNT_LOCKED = "YOUR ACCOUNT HAS BEEN LOCKED";
    public static final String METHOD_IS_NOT_ALLOWED = "REQUEST METHOD IS NOT SUPPORTED FOR THIS ENDPINT, PLEASE SEND A '%s' REQUEST";
    public static final String INTERNAL_SERVER_ERROR = "AN ERROR OCCUPED WHILE PROCESSING THE REQUEST";
    public static final String INCORRECT_CREDENTIALS = "USERNAME/PASSWARD INCORRECT. PLEASE TRY AGAIN";
    public static final String ACCOUNT_DISABLED = "YOUR ACCOUNT HAS BEEN DISABLED";
    public static final String ERROR_PROCESSING_FILE = "ERROR OCCURRED WHILE PROCESSING FILE ";
    public static final String NO_PERMISSIONS = "YOU DO NOT HAVE THE PERMISSION FOR THIS ENDPOINT";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST,ACCOUNT_DISABLED);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST,INCORRECT_CREDENTIALS);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<HttpResponse> accessDeniedException(){
        return createHttpResponse(HttpStatus.FORBIDDEN,NO_PERMISSIONS);
    }
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED,ACCOUNT_LOCKED);
    }
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpResponse> emailExistsException(EmailExistsException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotExistsException(EmailNotFoundException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
        return createHttpResponse(HttpStatus.UNAUTHORIZED,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpResponse> usernameExistsException(UsernameExistsException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(HttpRequestMethodNotSupportedException exception){
        LOGGER.error(exception.getMessage() );
        return  createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSuppportedException(HttpRequestMethodNotSupportedException exception){
        String[] supportedMethods = exception.getSupportedMethods();
        HttpMethod supportedMethod = HttpMethod.resolve(supportedMethods[0]);
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED,String.format(METHOD_IS_NOT_ALLOWED,supportedMethod));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> pageNotFoundException(NoHandlerFoundException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST,"PAGE NOT FOUND");
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus,String message){
        HttpResponse httpResponse = new HttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message
        );
        return new ResponseEntity<>(httpResponse,httpStatus);
    }
}
