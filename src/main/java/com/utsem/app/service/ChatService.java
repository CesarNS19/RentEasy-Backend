package com.utsem.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utsem.app.model.Mensaje;
import com.utsem.app.repository.MensajeRepository;

@Service
public class ChatService {

    private final MensajeRepository mensajeRepository;

    public ChatService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    public Mensaje enviarMensajeSeguro(Mensaje msg) {
        if (msg.getEmisorId() == null || msg.getReceptorId() == null || msg.getContenido() == null) {
            throw new RuntimeException("Mensaje incompleto");
        }

        String conversationId = normalizarConversation(
                msg.getEmisorId(), msg.getReceptorId());

        Mensaje nuevo = new Mensaje(
                msg.getEmisorId(),
                msg.getReceptorId(),
                msg.getContenido(),
                LocalDateTime.now(),
                conversationId
        );

        return mensajeRepository.save(nuevo);
    }

    public List<Mensaje> obtenerHistorialNormalizado(String conversationId) {
        String normalized = normalizarConversation(conversationId);
        return mensajeRepository.findByConversationIdOrderByFechaAsc(normalized);
    }

    public String normalizarConversation(Long a, Long b) {
        return (a < b) ? a + "-" + b : b + "-" + a;
    }

    public String normalizarConversation(String id) {
        try {
            String[] parts = id.split("-");
            Long a = Long.valueOf(parts[0]);
            Long b = Long.valueOf(parts[1]);
            return normalizarConversation(a, b);
        } catch (Exception e) {
            return id;
        }
    }
    
    public List<Object[]> listarConversaciones(Long userId) {
        return mensajeRepository.findLastMessages(userId);
    }

}
