package com.demo.mockapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MockServerErrorException extends RuntimeException {

    public MockServerErrorException(String message) {
        super(message);
    }

}
