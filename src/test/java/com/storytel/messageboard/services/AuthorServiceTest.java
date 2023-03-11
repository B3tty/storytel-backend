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

import com.storytel.messageboard.models.Author;
import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.repositories.AuthorRepository;
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
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author referenceAuthor;

    @BeforeEach
    void setUp() {
        referenceAuthor = new Author();
        referenceAuthor.setId(1);
        referenceAuthor.setName("Betty");
    }

    @Test
    void createAuthor() {
      when(authorRepository.save(any(Author.class))).thenReturn(referenceAuthor);

      Author result = authorService.createAuthor(referenceAuthor);

      assertNotNull(result);
      assertEquals(referenceAuthor.getId(), result.getId());
      assertEquals(referenceAuthor.getName(), result.getName());
      verify(authorRepository, times(1)).save(referenceAuthor);
    }

    @Test
    void getAuthorById() {
      when(authorRepository.findById(referenceAuthor.getId())).thenReturn(Optional.of(
          referenceAuthor));

      Author result = authorService.getAuthorById(referenceAuthor.getId());

      assertNotNull(result);
      assertEquals(referenceAuthor.getId(), result.getId());
      assertEquals(referenceAuthor.getName(), result.getName());
      verify(authorRepository, times(1)).findById(referenceAuthor.getId());
    }

    @Test
    void getAuthorByIdWhenNotFound() {
      when(authorRepository.findById(referenceAuthor.getId())).thenReturn(Optional.empty());

      Author result = authorService.getAuthorById(referenceAuthor.getId());

      assertNull(result);
      verify(authorRepository, times(1)).findById(referenceAuthor.getId());
    }

    @Test
    void getAllAuthors() {
      when(authorRepository.findAll()).thenReturn(new ArrayList<>(List.of(referenceAuthor)));
      List<Author> result = authorService.getAllAuthors();

      assertNotNull(result);
      assertEquals(1, result.size());
      assertEquals(referenceAuthor, result.get(0));
      verify(authorRepository, times(1)).findAll();
    }
}
