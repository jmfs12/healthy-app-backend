package com.backend.java.saude_e_bem_estar.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomErrorResponse err = new CustomErrorResponse();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Recurso não encontrado");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomErrorResponse> businessException(BusinessException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse err = new CustomErrorResponse();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Regra de negócio violada");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse> unauthorizedException(UnauthorizedException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        CustomErrorResponse err = new CustomErrorResponse();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Acesso não autorizado");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> defaultException(Exception e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        CustomErrorResponse err = new CustomErrorResponse();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Erro interno do servidor");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}