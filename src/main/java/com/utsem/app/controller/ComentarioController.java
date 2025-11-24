package com.utsem.app.controller;

import com.utsem.app.dto.ComentarioDTO;
import com.utsem.app.model.Comentario;
import com.utsem.app.service.ComentarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/crear")
    public Comentario crearComentario(@RequestBody ComentarioDTO dto) {
        return comentarioService.crearComentario(dto);
    }

    @GetMapping("/por-propiedad/{propiedadId}")
    public List<ComentarioDTO> listarPorPropiedad(@PathVariable Long propiedadId) {
        return comentarioService.listarPorPropiedad(propiedadId);
    }
}
