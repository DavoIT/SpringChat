package com.daves.chat.model;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "text", nullable = false)
    private String text;

    public Message() {
    }

    public Message(Long id, Long senderId, String text) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
    }

    public static Message fromJSON(JSONObject jsonObject) throws JSONException {
        Message message = new Message();
        message.chatId = jsonObject.getLong("chat_id");
        message.senderId = jsonObject.getLong("sender_id");
        message.text = jsonObject.getString("text");
        return message;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("id", id);
            result.put("sender_id", senderId);
            result.put("chat_id", chatId);
            result.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
