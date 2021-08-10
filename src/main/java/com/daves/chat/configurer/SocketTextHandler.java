package com.daves.chat.configurer;

import com.daves.chat.enums.SocketAction;
import com.daves.chat.exception.UserNotFoundException;
import com.daves.chat.model.Message;
import com.daves.chat.model.User;
import com.daves.chat.repository.MessageRepository;
import com.daves.chat.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage socketMessage)
            throws IOException, JSONException {
        String payload = socketMessage.getPayload();
        JSONObject jsonObject = new JSONObject(payload);

        SocketAction action = SocketAction.valueOf(jsonObject.getString("action"));
        JSONObject jsonToSend;
        TextMessage textMessageToSend;
        switch (action) {
            case login:
                Long userId = jsonObject.getLong("user_id");
//                try {
//                    User user = userRepository.getById(userId);
//                    jsonToSend = user.toJSON();
//                } catch (EntityNotFoundException e) {
//                    session.close(CloseStatus.BAD_DATA);
//                    throw new UserNotFoundException(userId);
//                }
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    jsonToSend = user.get().toJSON();
                } else {
                    session.close(CloseStatus.BAD_DATA);
                    throw new UserNotFoundException(userId);
                }
                break;
            case message:
                Message message = Message.fromJSON(jsonObject);
                Message resultMessage = messageRepository.save(message);
                jsonToSend = resultMessage.toJSON();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        jsonToSend.put("action", action.toString());
        textMessageToSend = new TextMessage(jsonToSend.toString());
        session.sendMessage(textMessageToSend);
        try {
            Thread.sleep(3000);
            session.sendMessage(textMessageToSend);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}