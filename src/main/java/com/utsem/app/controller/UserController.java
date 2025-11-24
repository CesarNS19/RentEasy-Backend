package com.utsem.app.controller;

import com.utsem.app.model.User;
import com.utsem.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam("username") String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            String imageUrl = null;

            if (image != null && !image.isEmpty()) {
                File directorio = new File(UPLOAD_DIR);
                if (!directorio.exists()) directorio.mkdirs();

                String nombreArchivo = StringUtils.cleanPath(image.getOriginalFilename());
                String nombreUnico = System.currentTimeMillis() + "_" + nombreArchivo;
                Path destino = Paths.get(UPLOAD_DIR + nombreUnico);
                Files.copy(image.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                imageUrl = "http://localhost:8081/uploads/" + nombreUnico;
            }

            User updatedUser = new User();
            updatedUser.setUsername(username);
            if (password != null && !password.trim().isEmpty()) {
                updatedUser.setPassword(password);
            }
            updatedUser.setImageUrl(imageUrl);

            return userService.updateUser(id, updatedUser)
                    .map(user -> ResponseEntity.ok().body(user))
                    .orElse(ResponseEntity.notFound().build());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario");
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }
}
