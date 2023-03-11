package com.storytel.messageboard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.storytel.messageboard.models.User;
import com.storytel.messageboard.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User referenceUser;

    @BeforeEach
    void setUp() {
        referenceUser = new User();
        referenceUser.setId(1);
        referenceUser.setName("Betty");
    }

    @Test
    void createUser() {
      when(userRepository.save(any(User.class))).thenReturn(referenceUser);

      User result = userService.createUser(referenceUser);

      assertNotNull(result);
      assertEquals(referenceUser.getId(), result.getId());
      assertEquals(referenceUser.getName(), result.getName());
      verify(userRepository, times(1)).save(referenceUser);
    }

    @Test
    void getUserById() {
      when(userRepository.findById(referenceUser.getId())).thenReturn(Optional.of(
          referenceUser));

      User result = userService.getUserById(referenceUser.getId());

      assertNotNull(result);
      assertEquals(referenceUser.getId(), result.getId());
      assertEquals(referenceUser.getName(), result.getName());
      verify(userRepository, times(1)).findById(referenceUser.getId());
    }

    @Test
    void getUserByIdWhenNotFound() {
      when(userRepository.findById(referenceUser.getId())).thenReturn(Optional.empty());

      User result = userService.getUserById(referenceUser.getId());

      assertNull(result);
      verify(userRepository, times(1)).findById(referenceUser.getId());
    }

    @Test
    void getAllUsers() {
      when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(referenceUser)));
      List<User> result = userService.getAllUsers();

      assertNotNull(result);
      assertEquals(1, result.size());
      assertEquals(referenceUser, result.get(0));
      verify(userRepository, times(1)).findAll();
    }
}
