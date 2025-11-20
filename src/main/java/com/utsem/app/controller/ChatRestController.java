package com.utsem.app.controller;

import org.springframework.web.bind.annotation.*;
import com.utsem.app.service.ChatService;
import com.utsem.app.model.Mensaje;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/history/{conversationId}")
    public List<Mensaje> getHistory(@PathVariable String conversationId) {
        // Normalizar ID desde backend
        return chatService.obtenerHistorialNormalizado(conversationId);
    }

    @PostMapping("/send")
    public Mensaje sendMessage(@RequestBody Mensaje mensaje) {
        return chatService.enviarMensajeSeguro(mensaje);
    }
}
