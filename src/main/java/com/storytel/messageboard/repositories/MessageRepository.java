package com.storytel.messageboard.repositories;

import com.storytel.messageboard.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

}