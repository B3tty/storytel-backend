package com.storytel.messageboard.services;

import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.models.Author;
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

    public Message createMessage(Message message, Author author) {
        message.setAuthor(author);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }

    public boolean deleteMessage(Long id, Author author) throws IllegalAuthorException {
        Message oldMessage = getMessageById(id);
        if (oldMessage != null) {
            if (oldMessage.getAuthor().getId() != (author.getId())) {
                throw new IllegalAuthorException();
            }
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Message updateMessage(Long id, Message newMessage, Author author) throws IllegalAuthorException {
        Message oldMessage = getMessageById(id);
        if (oldMessage == null) {
            return null;
        }
        if (oldMessage.getAuthor().getId() != (author.getId())) {
            throw new IllegalAuthorException();
        }

        oldMessage.setContent(newMessage.getContent());
        oldMessage.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(oldMessage);
        return oldMessage;
    }

    public class IllegalAuthorException extends Exception {
    }
}
