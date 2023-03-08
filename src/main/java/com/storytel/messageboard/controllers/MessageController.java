package com.storytel.messageboard.controllers;

import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.services.MessageService;
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
        messageService.createMessage(message);
        return (ResponseEntity.ok(message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable Long id, @RequestBody Message updatedMessage) {
        Message targetMessage = messageService.getMessageById(id);

        if (targetMessage == null) {
            throw new RuntimeException("Message not found");
        } else {
            if (targetMessage.getAuthor().equals(updatedMessage.getAuthor())) {
                messageService.updateMessage(id, updatedMessage);
                return (ResponseEntity.ok(updatedMessage));
            } else {
                throw new RuntimeException("You are not authorized to delete this message");
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable Long id, @RequestBody Message messageToDelete) {
        Message targetMessage = messageService.getMessageById(id);

        if (targetMessage == null) {
            throw new RuntimeException("Message not found");
        } else {
            if (targetMessage.getAuthor().equals(messageToDelete.getAuthor())) {
                messageService.deleteMessage(id);
            } else {
                throw new RuntimeException("You are not authorized to delete this message");
            }
        }
    }
}
