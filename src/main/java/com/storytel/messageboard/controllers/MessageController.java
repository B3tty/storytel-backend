package com.storytel.messageboard.controllers;

import com.storytel.messageboard.models.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
  private List<Message> messages = new ArrayList<>();

  @GetMapping
  public ResponseEntity<List<Message>> getAllMessages() {
    if (messages.isEmpty()) {
      return (ResponseEntity.notFound().build());
    } else {
      return (ResponseEntity.ok(messages));
    }
  }

  @PostMapping
  public ResponseEntity<Message> createMessage(@RequestBody Message message) {
    message.setId(messages.size() + 1);
    message.setTimestamp(new Date());
    messages.add(message);
    return (ResponseEntity.ok(message));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateMessageById(@PathVariable long id,
      @RequestBody Message updatedMessage) {
    for (Message message : messages) {
      if (message.getId() == id) {
        if (message.getAuthor().equals(updatedMessage.getAuthor())) {
          message.setText(updatedMessage.getText());
          return (ResponseEntity.ok(message));
        } else {
          throw new RuntimeException("You are not authorized to modify this message");
        }
      }
    }
    throw new RuntimeException("Message not found");
  }

  @DeleteMapping("/{id}")
  public void deleteMessageById(@PathVariable long id, @RequestBody Message messageToDelete) {
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
}
