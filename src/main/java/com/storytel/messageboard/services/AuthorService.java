package com.storytel.messageboard.services;

import com.storytel.messageboard.models.Author;
import com.storytel.messageboard.models.Message;
import com.storytel.messageboard.repositories.AuthorRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().forEach(author -> authors.add(author));
        return (authors);
    }

    public Author getAuthorById(Long authorId) {
        var author = authorRepository.findById(authorId);
        if (!author.isEmpty()) {
            return author.get();
        } else {
            return null;
        }
    }

    public Author createAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }
}
