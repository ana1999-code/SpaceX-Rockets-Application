package com.example.spacex.rockets.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;

    private final String message;

    private final HttpStatusCode statusCode;

    private final String path;
}
