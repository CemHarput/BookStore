package com.example.bookStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BookGetAllFailedException extends RuntimeException {
    public BookGetAllFailedException(String message) {
        super(message);
    }
}
