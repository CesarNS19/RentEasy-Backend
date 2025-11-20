package com.utsem.app.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.utsem.app.dto.ChatMessageDTO;
import com.utsem.app.model.Mensaje;
import com.utsem.app.service.ChatService;

@Controller
public class ChatStompController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatStompController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send/{conversationId}")
    public void sendMessage(@DestinationVariable String conversationId, ChatMessageDTO payload) {
        Mensaje saved = chatService.enviarMensajeSeguro(
                new Mensaje(payload.getEmisorId(),
                        payload.getReceptorId(),
                        payload.getContenido(),
                        null,
                        conversationId)
        );

        messagingTemplate.convertAndSend("/topic/conversations/" + saved.getConversationId(), saved);
    }
    
    @MessageMapping("/chat.typing/{conversationId}")
    public void typing(@DestinationVariable String conversationId, ChatMessageDTO payload) {
        messagingTemplate.convertAndSend("/topic/typing/" + conversationId, payload);
    }
}
