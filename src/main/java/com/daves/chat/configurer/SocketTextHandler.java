package com.daves.chat.configurer;

import com.daves.chat.enums.SocketMessageType;
import com.daves.chat.model.Message;
import com.daves.chat.model.SocketLoginRequestMessage;
import com.daves.chat.model.SocketMessage;
import com.daves.chat.repository.MessageRepository;
import com.daves.chat.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class SocketTextHandler extends TextWebSocketHandler {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage textMessage) {
        String payload = textMessage.getPayload();
        try {
            JSONObject receivedPayloadJSON = new JSONObject(payload);
            SocketMessage receivedSocketMessage = SocketMessage.fromJSON(receivedPayloadJSON);
            SocketMessageType type = SocketMessageType.valueOf(receivedPayloadJSON.getString("type"));
            JSONObject jsonToSend = new JSONObject();
            switch (type) {
                case login:
                    handleLoginMessage(session, (SocketLoginRequestMessage) receivedSocketMessage);
                    break;
                case message:
                    Message message = Message.fromJSON(receivedPayloadJSON);
                    Message resultMessage = messageRepository.save(message);
                    jsonToSend = resultMessage.toJSON();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            TextMessage textMessageToSend = new TextMessage(jsonToSend.toString());
            try {
                session.sendMessage(textMessageToSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            SocketMessage errorMessage = new SocketMessage(SocketMessageType.error);
            errorMessage.putData("reason", e.getCause());
            try {
                session.sendMessage(new TextMessage(errorMessage.toString()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        session.sendMessage(new TextMessage("Welcome to Daves socket! Let's start!"));
    }

    private void handleLoginMessage(@NotNull WebSocketSession session, SocketLoginRequestMessage receivedSocketMessage) {

    }
}