package com.smartStudy.Planner.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse();
        customExceptionResponse.setMessage(e.getMessage());
        customExceptionResponse.setPath(request.getRequestURI());
        customExceptionResponse.setStatus(HttpStatus.UNAUTHORIZED);
        customExceptionResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(customExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse();
        customExceptionResponse.setMessage(e.getMessage());
        customExceptionResponse.setPath(request.getRequestURI());
        customExceptionResponse.setStatus(HttpStatus.FORBIDDEN);
        customExceptionResponse.setStatusCode(HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(customExceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJwtException(MalformedJwtException e, HttpServletRequest request) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse();
        customExceptionResponse.setMessage(e.getMessage());
        customExceptionResponse.setPath(request.getRequestURI());
        customExceptionResponse.setStatus(HttpStatus.UNAUTHORIZED);
        customExceptionResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(customExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException e, HttpServletRequest request) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse();
        customExceptionResponse.setMessage(e.getMessage());
        customExceptionResponse.setPath(request.getRequestURI());
        customExceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        customExceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(customExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse();
        customExceptionResponse.setMessage(e.getMessage());
        customExceptionResponse.setPath(request.getRequestURI());
        customExceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        customExceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(customExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e, HttpServletRequest request) {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse();
        customExceptionResponse.setMessage(e.getMessage());
        customExceptionResponse.setPath(request.getRequestURI());
        customExceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        customExceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(customExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
