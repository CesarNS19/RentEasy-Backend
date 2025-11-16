package com.utsem.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:8100")
public class FileUploadController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @PostMapping("/imagen")
    public ResponseEntity<String> subirImagen(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Archivo vacío", HttpStatus.BAD_REQUEST);
        }

        try {
            File directorio = new File(UPLOAD_DIR);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = StringUtils.cleanPath(file.getOriginalFilename());

            String nombreUnico = System.currentTimeMillis() + "_" + nombreArchivo;

            Path destino = Paths.get(UPLOAD_DIR + nombreUnico);

            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            String urlPublica = "http://localhost:8081/uploads/" + nombreUnico;

            return new ResponseEntity<>(urlPublica, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al subir la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
