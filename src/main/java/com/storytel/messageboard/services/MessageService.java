package com.storytel.messageboard.services;

import com.storytel.messageboard.models.Message;
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

    public Message getMessageById(Integer messageId) {
        return (messageRepository.findById(messageId).get());
    }

    public void createMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    public void updateMessage(Integer id, Message newMessage) {
        Message oldMessage = getMessageById(id);
        newMessage.setId(id);
        newMessage.setCreatedAt(oldMessage.getCreatedAt());
        newMessage.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(newMessage);
    }
}
