package com.storytel.messageboard.services;

import static org.junit.jupiter.api.Assertions.*;

import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.repositories.MessageRepository;
import com.storytel.messageboard.services.MessageService.IllegalAuthorException;
import javax.swing.text.html.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    private Message referenceMessage;

    @BeforeEach
    void setUp() {
        referenceMessage = new Message();
        referenceMessage.setId(1L);
        referenceMessage.setAuthor("Betty");
        referenceMessage.setContent("Hello world!");
    }

    @Test
    void createMessage() {
      when(messageRepository.save(any(Message.class))).thenReturn(referenceMessage);

      Message result = messageService.createMessage(referenceMessage);

      assertNotNull(result);
      assertEquals(referenceMessage.getId(), result.getId());
      assertEquals(referenceMessage.getAuthor(), result.getAuthor());
      assertEquals(referenceMessage.getContent(), result.getContent());
      verify(messageRepository, times(1)).save(referenceMessage);
    }

    @Test
    void getMessageById() {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(
          referenceMessage));

      Message result = messageService.getMessageById(referenceMessage.getId());

      assertNotNull(result);
      assertEquals(referenceMessage.getId(), result.getId());
      assertEquals(referenceMessage.getAuthor(), result.getAuthor());
      assertEquals(referenceMessage.getContent(), result.getContent());
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
    }

    @Test
    void getMessageByIdWhenNotFound() {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.empty());

      Message result = messageService.getMessageById(referenceMessage.getId());

      assertNull(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
    }

    @Test
    void updateMessageById() throws IllegalAuthorException {
        Message requestedMessage = new Message();
        requestedMessage.setAuthor("Betty");
        requestedMessage.setContent("Updated message");

        when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(referenceMessage));
        when(messageRepository.save(any(Message.class))).thenReturn(requestedMessage);

        Message result = messageService.updateMessage(referenceMessage.getId(), requestedMessage);

        assertNotNull(result);
        assertEquals(referenceMessage.getId(), result.getId());
        assertEquals(requestedMessage.getAuthor(), result.getAuthor());
        assertEquals(requestedMessage.getContent(), result.getContent());
        assertEquals(requestedMessage.getCreatedAt(), result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        verify(messageRepository, times(1)).findById(referenceMessage.getId());
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void updateMessageByIdWhenNotFound() throws IllegalAuthorException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.empty());

      Message result = messageService.updateMessage(referenceMessage.getId(), referenceMessage);

      assertNull(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(0)).save(referenceMessage);
    }

    @Test
    void deleteMessageById() throws IllegalAuthorException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(referenceMessage));

      boolean result = messageService.deleteMessage(referenceMessage.getId(), referenceMessage);

      assertTrue(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(1)).deleteById(referenceMessage.getId());
    }

    @Test
    void deleteMessageByIdWhenNotFound() throws IllegalAuthorException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.empty());

      boolean result = messageService.deleteMessage(referenceMessage.getId(), referenceMessage);

      assertFalse(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(0)).deleteById(referenceMessage.getId());
    }

    @Test
    void getAllMessages() {
    }
}
