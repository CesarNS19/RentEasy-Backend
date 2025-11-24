package com.utsem.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import com.utsem.app.dto.ChatMessageDTO;
import com.utsem.app.dto.ConversacionDTO;
import com.utsem.app.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/history/{conversationId}")
    public List<ChatMessageDTO> getHistory(@PathVariable String conversationId) {
        return chatService.obtenerHistorialNormalizado(conversationId);
    }

    @PostMapping("/send")
    public ChatMessageDTO sendMessage(@RequestBody ChatMessageDTO mensaje) {
        return chatService.enviarMensajeSeguro(mensaje);
    }

    @GetMapping("/conversaciones/{userId}")
    public List<ConversacionDTO> listarConversaciones(@PathVariable Long userId) {
        return chatService.listarConversaciones(userId);
    }

    @PostMapping("/mark-read")
	    public void marcarComoLeidos(@RequestBody Map<String, Object> payload) {
	        String conversationId = (String) payload.get("conversationId");
	        Long userId = Long.valueOf(payload.get("userId").toString());
	        chatService.marcarMensajesComoLeidos(conversationId, userId);
	    }

    @MessageMapping("/chat.send/{conversationId}")
    @SendTo("/topic/conversations/{conversationId}")
    public ChatMessageDTO send(@DestinationVariable String conversationId, ChatMessageDTO mensaje) {
        return chatService.enviarMensajeSeguro(mensaje);
    }
}
