package com.example.blog.bean;

import com.example.blog.consts.Security;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
public class HttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM-dd-yyyy hh:mm:ss",timezone = Security.timeZone)
    private Date timeSamp;
    private int httpStatusCode; // 200,100,400
    private HttpStatus httpStatus;
    private String reason; // Http Reason (Libelle d Http responce)
    private String message; // Developper Msg
    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.timeSamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
