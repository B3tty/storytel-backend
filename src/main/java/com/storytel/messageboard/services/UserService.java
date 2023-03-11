package com.storytel.messageboard.services;

import com.storytel.messageboard.models.User;
import com.storytel.messageboard.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return (users);
    }

    public User getUserById(Long userId) {
        var user = userRepository.findById(userId);
        if (!user.isEmpty()) {
            return user.get();
        } else {
            return null;
        }
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }
}
