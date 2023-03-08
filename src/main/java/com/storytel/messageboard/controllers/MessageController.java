package com.storytel.messageboard.controllers;

import com.storytel.messageboard.models.Message;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
  private static Message welcomeMessage = new Message();
  private List<Message> messages = new ArrayList<>();

  public MessageController() {
    welcomeMessage.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    welcomeMessage.setContent("Welcome to the Board!");
    welcomeMessage.setAuthor("Admin");
    welcomeMessage.setTimestamp(LocalDateTime.now());
    messages.add(welcomeMessage);
  }

  @GetMapping
  public ResponseEntity<List<Message>> getAllMessages() {
    if (messages.isEmpty()) {
      return (ResponseEntity.notFound().build());
    } else {
      return (ResponseEntity.ok(messages));
    }
  }

  @PostMapping
  public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) {
    message.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    message.setTimestamp(LocalDateTime.now());
    messages.add(message);
    return (ResponseEntity.ok(message));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateMessageById(@PathVariable Long id,
      @RequestBody Message updatedMessage) {
    for (Message message : messages) {
      if (message.getId() == id) {
        if (message.getAuthor().equals(updatedMessage.getAuthor())) {
          message.setContent(updatedMessage.getContent());
          return (ResponseEntity.ok(message));
        } else {
          throw new RuntimeException("You are not authorized to modify this message");
        }
      }
    }
    throw new RuntimeException("Message not found");
  }

  @DeleteMapping("/{id}")
  public void deleteMessageById(@PathVariable Long id, @RequestBody Message messageToDelete) {
    for (Message message : messages) {
      if (message.getId() == id) {
        if (message.getAuthor().equals(messageToDelete.getAuthor())) {
          messages.remove(message);
          return;
        } else {
          throw new RuntimeException("You are not authorized to delete this message");
        }
      }
    }
    throw new RuntimeException("Message not found");
  }

  public class PostMessageRequest {
    public String content;
    public String author;

  }
}
