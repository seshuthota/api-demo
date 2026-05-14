package com.demo.mockapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MockUnauthorizedException extends RuntimeException {

    public MockUnauthorizedException(String message) {
        super(message);
    }

}
