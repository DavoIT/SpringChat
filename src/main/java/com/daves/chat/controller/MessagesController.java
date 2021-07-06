package com.daves.chat.controller;

import com.daves.chat.model.Message;
import com.daves.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class MessagesController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<Message> getMessagesForChat(@RequestParam Long id) {
        return messageRepository.findAll();
    }

    @PostMapping("/messages")
    public ResponseEntity<Object> addMessage(@RequestBody Message message) {
        Message createdMessage = messageRepository.save(message);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMessage.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
