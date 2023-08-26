package com.example.bookStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserCannotBeFoundException extends RuntimeException {
    public UserCannotBeFoundException(String message) {
        super(message);
    }
}
