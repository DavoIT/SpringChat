package com.daves.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(Long id) {
        super("No chat found with id: " + id);
    }

    public ChatNotFoundException(String username) {
        super("No chat found where one of participants is: " + username);
    }
}
