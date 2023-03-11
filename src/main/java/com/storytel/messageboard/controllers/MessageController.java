package com.storytel.messageboard.controllers;

import com.storytel.messageboard.models.Author;
import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.services.MessageService;
import com.storytel.messageboard.services.MessageService.IllegalAuthorException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        if (messages.isEmpty()) {
            return (ResponseEntity.notFound().build());
        } else {
            return (ResponseEntity.ok(messages));
        }
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) {
        var okMessage = messageService.createMessage(message);
        return (ResponseEntity.ok(okMessage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable Long id, @RequestBody Message updatedMessage) {
        try {
            Message resultMessage = messageService.updateMessage(id, updatedMessage);
            if (resultMessage == null) {
                return ResponseEntity.notFound().build();
            }
            return (ResponseEntity.ok(resultMessage));
        } catch (IllegalAuthorException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMessageById(@PathVariable Long id, @RequestBody Message messageToDelete) {
        try {
            Message resultMessage = messageService.updateMessage(id, messageToDelete);
            if (resultMessage == null) {
                return ResponseEntity.notFound().build();
            }
            return (ResponseEntity.ok().build());
        } catch (IllegalAuthorException e) {
            return ResponseEntity.status(401).build();
        }
    }

    public class PostRequest {
        Message message;
    }
}
