package com.daves.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.daves.chat.exception.ChatNotFoundException;
import com.daves.chat.model.Chat;
import com.daves.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ChatController {
    @Autowired
    ChatRepository chatRepository;

    @GetMapping("/all-chats")
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @DeleteMapping("/chats")
    public void deleteChatById(@RequestParam Long id) {
        if (!chatRepository.existsById(id)) {
            throw new ChatNotFoundException(id);
        }
        chatRepository.deleteById(id);
    }

    @PostMapping("/chats")
    public ResponseEntity<Object> addChat(@RequestBody Chat chat) {
        Chat newChat = chatRepository.save(chat);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newChat.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/chats")
    public void changeChatName(@RequestBody Map<String, Object> chat) {
        JSONObject jsonObject = new JSONObject(chat);
        Long chatId = jsonObject.getLong("id");
        String chatName = jsonObject.getString("name");
        Optional<Chat> c = chatRepository.findById(chatId);
        if (c.isPresent()) {
            Chat existingChat = c.get();
            existingChat.setName(chatName);
            chatRepository.save(existingChat);
        } else {
            throw new ChatNotFoundException(chatId);
        }
    }

}
