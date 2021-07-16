package com.daves.chat.controller;

import com.daves.chat.model.Message;
import com.daves.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessagesController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<Message> getMessagesForChat(@RequestParam Long id) {
        return messageRepository.getMessagesForChat(id);
    }

    @PostMapping("/messages")
    public Message addMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

}
