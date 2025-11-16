package com.utsem.app.controller;

import com.utsem.app.dto.MensajeDTO;
import com.utsem.app.model.Mensaje;
import com.utsem.app.model.User;
import com.utsem.app.repository.UserRepository;
import com.utsem.app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat.enviar")
    public void enviarMensaje(@Payload MensajeDTO dto) {
        User emisor = userRepository.findById(dto.getEmisorId())
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado"));
        User receptor = userRepository.findById(dto.getReceptorId())
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));

        Mensaje mensaje = chatService.enviarMensaje(dto, emisor, receptor);

        messagingTemplate.convertAndSendToUser(
                receptor.getId().toString(),
                "/queue/mensajes",
                mensaje
        );
    }
}