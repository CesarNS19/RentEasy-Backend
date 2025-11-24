package com.utsem.app.service;

import com.utsem.app.model.User;
import com.utsem.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {

            if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
                existingUser.setUsername(updatedUser.getUsername());
            }

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            if (updatedUser.getImageUrl() != null && !updatedUser.getImageUrl().isEmpty()) {
                existingUser.setImageUrl(updatedUser.getImageUrl());
            }

            return userRepository.save(existingUser);
        });
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
