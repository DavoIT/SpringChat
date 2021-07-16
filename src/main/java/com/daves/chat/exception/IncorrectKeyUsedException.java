package com.daves.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectKeyUsedException extends RuntimeException {

    public IncorrectKeyUsedException() {
        super("Incorrect keyword is used");
    }
}
