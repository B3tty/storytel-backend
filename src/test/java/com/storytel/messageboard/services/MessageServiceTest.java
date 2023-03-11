package com.storytel.messageboard.services;

import static org.junit.jupiter.api.Assertions.*;

import com.storytel.messageboard.models.User;
import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.repositories.MessageRepository;
import com.storytel.messageboard.services.MessageService.IllegalUserException;
import java.util.ArrayList;
import java.util.List;
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
    private User referenceUser;
    private User differentUser;

    @BeforeEach
    void setUp() {
        referenceUser = new User();
        referenceUser.setId(1);
        referenceUser.setName("Betty");

        differentUser = new User();
        differentUser.setId(2);
        differentUser.setName("Eve");

        referenceMessage = new Message();
        referenceMessage.setId(1L);
        referenceMessage.setUser(referenceUser);
        referenceMessage.setContent("Hello world!");
    }

    @Test
    void createMessage() {
      when(messageRepository.save(any(Message.class))).thenReturn(referenceMessage);

      Message result = messageService.createMessage(referenceMessage, referenceUser);

      assertNotNull(result);
      assertEquals(referenceMessage.getId(), result.getId());
      assertEquals(referenceMessage.getUser(), result.getUser());
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
      assertEquals(referenceMessage.getUser(), result.getUser());
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
    void updateMessageById() throws IllegalUserException {
        Message requestedMessage = new Message();
        requestedMessage.setContent("Updated message");
        requestedMessage.setUser(referenceUser);

        when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(referenceMessage));
        when(messageRepository.save(any(Message.class))).thenReturn(requestedMessage);

        Message result = messageService.updateMessage(referenceMessage.getId(), requestedMessage,
            referenceUser);

        assertNotNull(result);
        assertEquals(referenceMessage.getId(), result.getId());
        assertEquals(requestedMessage.getUser(), result.getUser());
        assertEquals(requestedMessage.getContent(), result.getContent());
        assertEquals(requestedMessage.getCreatedAt(), result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        verify(messageRepository, times(1)).findById(referenceMessage.getId());
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void updateMessageByIdWhenNotFound() throws IllegalUserException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.empty());

      Message result = messageService.updateMessage(referenceMessage.getId(), referenceMessage,
          referenceUser);

      assertNull(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(0)).save(referenceMessage);
    }

    @Test
    void updateMessageByIdWhenDifferentUser() throws IllegalUserException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(referenceMessage));

      Message requestMessage = new Message();
      requestMessage.setId(referenceMessage.getId());

      assertThrows(
          IllegalUserException.class,
          () -> messageService.updateMessage(referenceMessage.getId(), requestMessage,
              differentUser)
      );

      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(0)).save(any(Message.class));
    }

    @Test
    void deleteMessageById() throws IllegalUserException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(referenceMessage));

      boolean result = messageService.deleteMessage(referenceMessage.getId(), referenceUser);

      assertTrue(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(1)).deleteById(referenceMessage.getId());
    }

    @Test
    void deleteMessageByIdWhenNotFound() throws IllegalUserException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.empty());

      boolean result = messageService.deleteMessage(referenceMessage.getId(), referenceUser);

      assertFalse(result);
      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(0)).deleteById(referenceMessage.getId());
    }

    @Test
    void deleteMessageByIdWhenDifferentUser() throws IllegalUserException {
      when(messageRepository.findById(referenceMessage.getId())).thenReturn(Optional.of(referenceMessage));

      Message requestMessage = new Message();
      requestMessage.setId(referenceMessage.getId());

      assertThrows(
          IllegalUserException.class,
          () -> messageService.deleteMessage(referenceMessage.getId(), differentUser)
      );

      verify(messageRepository, times(1)).findById(referenceMessage.getId());
      verify(messageRepository, times(0)).deleteById(referenceMessage.getId());
    }

    @Test
    void getAllMessages() {
      when(messageRepository.findAll()).thenReturn(new ArrayList<>(List.of(referenceMessage)));
      List<Message> result = messageService.getAllMessages();

      assertNotNull(result);
      assertEquals(1, result.size());
      assertEquals(referenceMessage, result.get(0));
      verify(messageRepository, times(1)).findAll();
    }
}
