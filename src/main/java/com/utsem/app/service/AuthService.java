package com.utsem.app.service;

import com.utsem.app.model.User;
import com.utsem.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(String username, String password, String role) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Usuario ya existe");
        }
        User user = new User(username, password, role);
        return userRepository.save(user);
    }

    public User login(String username, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) throw new Exception("Usuario no encontrado");

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) throw new Exception("Contraseña incorrecta");

        return user;
    }
}
