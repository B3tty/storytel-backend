package com.storytel.messageboard.controllers;

import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.models.User;
import com.storytel.messageboard.services.MessageService;
import com.storytel.messageboard.services.MessageService.IllegalUserException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Message> createMessage(@Valid @RequestBody MessageRequest messageRequest) {
        var okMessage = messageService.createMessage(messageRequest.message, messageRequest.user);
        return (ResponseEntity.ok(okMessage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable Long id,
            @Valid @RequestBody MessageRequest updateMessageRequest) {
        try {
            Message resultMessage = messageService.updateMessage(id, updateMessageRequest.message,
                    updateMessageRequest.user);
            if (resultMessage == null) {
                return ResponseEntity.notFound().build();
            }
            return (ResponseEntity.ok(resultMessage));
        } catch (IllegalUserException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMessageById(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            boolean resultMessage = messageService.deleteMessage(id, user);
            if (!resultMessage) {
                return ResponseEntity.notFound().build();
            }
            return (ResponseEntity.ok().build());
        } catch (IllegalUserException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageRequest {
        Message message;
        User user;
    }
}
