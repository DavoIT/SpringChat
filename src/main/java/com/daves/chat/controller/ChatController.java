package com.daves.chat.controller;

import com.daves.chat.exception.ChatNotFoundException;
import com.daves.chat.model.Chat;
import com.daves.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ChatController {
    @Autowired
    ChatRepository chatRepository;

    @GetMapping("/chats")
    public Chat getChatWithParticipants(@RequestParam Long participantId1, @RequestParam Long participantId2) {
        Chat chat = chatRepository.getChatWithParticipants(participantId1, participantId2);
        if (chat == null) {
            return (Chat) addChat(new Chat(participantId1, participantId2)).getBody();
        } else {
            return chat;
        }
    }

    @GetMapping("/all-chats")
    public List<Chat> getChatWithParticipant(@RequestParam Long id) {
        return chatRepository.getChatsWithParticipant(id);
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

}
