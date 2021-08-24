package com.daves.chat.model;

import com.daves.chat.enums.SocketMessageType;
import org.json.JSONException;

public class SocketErrorMessage extends SocketMessage {
    public SocketErrorMessage(String reason) throws JSONException {
        super(SocketMessageType.error);
        data.put("reason", reason);
    }
}
