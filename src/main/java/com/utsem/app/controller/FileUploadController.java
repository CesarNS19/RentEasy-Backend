package com.utsem.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:8100")
public class FileUploadController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @PostMapping("/imagenes")
    public ResponseEntity<List<String>> subirImagenes(@RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            File directorio = new File(UPLOAD_DIR);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            List<String> urls = new ArrayList<>();

            for (MultipartFile file : files) {
                String nombreArchivo = StringUtils.cleanPath(file.getOriginalFilename());
                String nombreUnico = System.currentTimeMillis() + "_" + nombreArchivo;
                Path destino = Paths.get(UPLOAD_DIR + nombreUnico);
                Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                String urlPublica = "http://localhost:8081/uploads/" + nombreUnico;
                urls.add(urlPublica);
            }

            return new ResponseEntity<>(urls, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
