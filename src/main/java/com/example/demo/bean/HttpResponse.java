package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private int httpStatusCode; // 200,100,400
    private HttpStatus httpStatus;
    private String reason; // Http Reason (Libelle d Http responce)
    private String message; // Developper Msg
}
