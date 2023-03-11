package com.storytel.messageboard.repositories;

import com.storytel.messageboard.models.Author;
import com.storytel.messageboard.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}