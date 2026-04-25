package com.smartStudy.Planner.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class CustomExceptionResponse {

    private Instant timestamp;
    private Integer statusCode;
    private HttpStatus status;
    private String message;
    private String path;

    public CustomExceptionResponse(){
        this.timestamp = Instant.now();
    }

    public CustomExceptionResponse(int statusCode, HttpStatus status, String message) {
        this.timestamp = Instant.now();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

}
