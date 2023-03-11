package com.storytel.messageboard.services;

import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.repositories.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach(message -> messages.add(message));
        return (messages);
    }

    public Message getMessageById(Long messageId) {
        var message = messageRepository.findById(messageId);
        if (!message.isEmpty()) {
            return message.get();
        } else {
            return null;
        }
    }

    public Message createMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }

    public boolean deleteMessage(Long id, Message messageToDelete) throws IllegalAuthorException {
        Message oldMessage = getMessageById(id);
        if (oldMessage != null) {
            if (oldMessage.getAuthor() != messageToDelete.getAuthor()) {
                throw new IllegalAuthorException();
            }
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Message updateMessage(Long id, Message newMessage) throws IllegalAuthorException {
        Message oldMessage = getMessageById(id);
        if (oldMessage == null) {
            return null;
        }
        if (oldMessage.getAuthor() != newMessage.getAuthor()) {
            throw new IllegalAuthorException();
        }

        newMessage.setId(id);
        newMessage.setCreatedAt(oldMessage.getCreatedAt());
        newMessage.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(newMessage);
        return newMessage;
    }

    public class IllegalAuthorException extends Exception {
    }
}
