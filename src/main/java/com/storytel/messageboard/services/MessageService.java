package com.storytel.messageboard.services;

import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.models.User;
import com.storytel.messageboard.repositories.MessageRepository;
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

    public Message createMessage(Message message, User user) {
        message.setUser(user);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }

    public boolean deleteMessage(Long id, User user) throws IllegalUserException {
        Message oldMessage = getMessageById(id);
        if (oldMessage != null) {
            if (oldMessage.getUser().getId() != (user.getId())) {
                throw new IllegalUserException();
            }
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Message updateMessage(Long id, Message newMessage, User user) throws IllegalUserException {
        Message oldMessage = getMessageById(id);
        if (oldMessage == null) {
            return null;
        }
        if (oldMessage.getUser().getId() != (user.getId())) {
            throw new IllegalUserException();
        }

        oldMessage.setContent(newMessage.getContent());
        oldMessage.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(oldMessage);
        return oldMessage;
    }

    public class IllegalUserException extends Exception {
    }
}
