package com.example.spacex.rockets.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> onResourceNotFound(HttpServletRequest request,
                                                            ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(LocalDateTime.now(), exception.getMessage(), HttpStatus.NOT_FOUND, request.getServletPath()));
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> onResourceDuplicate(HttpServletRequest request,
                                                             DuplicateResourceException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(LocalDateTime.now(), exception.getMessage(), HttpStatus.CONFLICT, request.getServletPath()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> onHttpMessageNotReadable(HttpServletRequest request,
                                                                  HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(LocalDateTime.now(), exception.getMessage(), HttpStatus.BAD_REQUEST, request.getServletPath()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> onMethodArgumentNotValid(HttpServletRequest request,
                                                                  MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String message = makeErrorMessage(fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(LocalDateTime.now(), message, HttpStatus.BAD_REQUEST, request.getServletPath()));
    }

    private String makeErrorMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append("Field: ").append(error.getField())
                    .append(" has invalid value: ")
                    .append(error.getRejectedValue())
                    .append(", ");
        }
        return builder.substring(0, builder.length() - 2);
    }
}