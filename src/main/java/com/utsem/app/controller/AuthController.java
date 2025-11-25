package com.utsem.app.controller;

import com.utsem.app.model.User;
import com.utsem.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:8100", "https://renteasy-frontend.com"}, // puedes agregar el dominio real después
             allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
             allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registro")
    public Map<String, Object> register(@RequestBody User request) throws Exception {
        User user = authService.register(request.getUsername(), request.getPassword(), request.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("token", "dummy");
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User request) throws Exception {
        User user = authService.login(request.getUsername(), request.getPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("token", "dummy");
        return response;
    }

    @GetMapping("/test")
    public String test() {
        return "🚀 RentEasy Backend activo!";
    }
}
