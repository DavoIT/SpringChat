package com.daves.chat.model;

import com.daves.chat.enums.SocketMessageType;

public class SocketLoginRequestMessage extends SocketMessage {
    public SocketLoginRequestMessage(Long userId) {
        super(SocketMessageType.login);
    }
}
