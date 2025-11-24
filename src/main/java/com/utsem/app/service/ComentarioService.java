package com.utsem.app.service;

import com.utsem.app.model.Comentario;
import com.utsem.app.model.Propiedad;
import com.utsem.app.model.User;
import com.utsem.app.repository.ComentarioRepository;
import com.utsem.app.repository.PropiedadRepository;
import com.utsem.app.repository.UserRepository;
import com.utsem.app.dto.ComentarioDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepo;

    @Autowired
    private PropiedadRepository propiedadRepo;

    @Autowired
    private UserRepository userRepo;

    public Comentario crearComentario(ComentarioDTO dto) {
        User usuario = userRepo.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Propiedad propiedad = propiedadRepo.findById(dto.getPropiedadId())
            .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setPropiedad(propiedad);
        comentario.setMensaje(dto.getMensaje());
        comentario.setEstrellas(dto.getEstrellas());

        return comentarioRepo.save(comentario);
    }

    public List<ComentarioDTO> listarPorPropiedad(Long propiedadId) {
        return comentarioRepo.findByPropiedadId(propiedadId).stream()
            .map(c -> new ComentarioDTO(
                c.getMensaje(),
                c.getEstrellas(),
                c.getPropiedad().getId(),
                c.getUsuario().getId(),
                c.getUsuario().getUsername(),
                c.getFecha(),
                c.getUsuario().getImageUrl()
            ))
            .collect(Collectors.toList());
    }
}
