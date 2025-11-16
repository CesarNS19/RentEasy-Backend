package com.utsem.app.controller;

import com.utsem.app.dto.MensajeDTO;
import com.utsem.app.model.*;
import com.utsem.app.repository.UserRepository;
import com.utsem.app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/historial/{conversacionId}")
    public ResponseEntity<List<Mensaje>> obtenerMensajes(@PathVariable Long conversacionId) {
        try {
            return ResponseEntity.ok(chatService.obtenerMensajes(conversacionId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/iniciar/{usuario1Id}/{usuario2Id}")
    public ResponseEntity<Conversacion> iniciarConversacion(
            @PathVariable Long usuario1Id,
            @PathVariable Long usuario2Id) {

        User u1 = userRepository.findById(usuario1Id)
                .orElseThrow(() -> new RuntimeException("Usuario 1 no encontrado"));
        User u2 = userRepository.findById(usuario2Id)
                .orElseThrow(() -> new RuntimeException("Usuario 2 no encontrado"));

        Conversacion conversacion = chatService.iniciarConversacion(u1, u2);
        return ResponseEntity.ok(conversacion);
    }

    @PostMapping("/enviar")
    public Mensaje enviarMensaje(@RequestBody MensajeDTO dto) {
        User emisor = userRepository.findById(dto.getEmisorId())
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado"));
        User receptor = userRepository.findById(dto.getReceptorId())
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));
        return chatService.enviarMensaje(dto, emisor, receptor);
    }
}
