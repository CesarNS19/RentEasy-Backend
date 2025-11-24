package com.utsem.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsem.app.dto.ChatMessageDTO;
import com.utsem.app.dto.ConversacionDTO;
import com.utsem.app.model.Mensaje;
import com.utsem.app.repository.MensajeRepository;
import com.utsem.app.repository.UserRepository;

@Service
public class ChatService {
	
	private final MensajeRepository mensajeRepository;
    private final UserRepository userRepository;

    public ChatService(MensajeRepository mensajeRepository, UserRepository userRepository) {
        this.mensajeRepository = mensajeRepository;
        this.userRepository = userRepository;
    }

    public ChatMessageDTO enviarMensajeSeguro(ChatMessageDTO dto) {
        if (dto.getEmisorId() == null || dto.getReceptorId() == null || dto.getContenido() == null) {
            throw new RuntimeException("Mensaje incompleto");
        }

        String conversationId = normalizarConversation(dto.getEmisorId(), dto.getReceptorId());

        Mensaje nuevo = new Mensaje(
                dto.getEmisorId(),
                dto.getReceptorId(),
                dto.getContenido(),
                LocalDateTime.now(),
                conversationId
        );

        Mensaje guardado = mensajeRepository.save(nuevo);

        ChatMessageDTO result = new ChatMessageDTO();
        result.setEmisorId(guardado.getEmisorId());
        result.setReceptorId(guardado.getReceptorId());
        result.setContenido(guardado.getContenido());
        result.setConversationId(guardado.getConversationId());
        result.setFecha(guardado.getFecha().toString());

        return result;
    }

    public List<ChatMessageDTO> obtenerHistorialNormalizado(String conversationId) {
        String normalized = normalizarConversation(conversationId);

        return mensajeRepository.findByConversationIdOrderByFechaAsc(normalized)
                .stream()
                .map(m -> {
                    ChatMessageDTO dto = new ChatMessageDTO();
                    dto.setEmisorId(m.getEmisorId());
                    dto.setReceptorId(m.getReceptorId());
                    dto.setContenido(m.getContenido());
                    dto.setConversationId(m.getConversationId());
                    dto.setFecha(m.getFecha().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ConversacionDTO> listarConversaciones(Long userId) {
        return mensajeRepository.findLastMessages(userId)
                .stream()
                .map(r -> {
                    String conversationId = (String) r[0];
                    Long emisorId = ((Number) r[1]).longValue();
                    Long receptorId = ((Number) r[2]).longValue();
                    String emisorName = (String) r[3];
                    String receptorName = (String) r[4];
                    String contenido = (String) r[5];

                    LocalDateTime fecha;
                    Object r6 = r[6];
                    if (r6 instanceof java.sql.Timestamp) {
                        fecha = ((java.sql.Timestamp) r6).toLocalDateTime();
                    } else if (r6 instanceof String) {
                        fecha = LocalDateTime.parse((String) r6);
                    } else {
                        fecha = LocalDateTime.now();
                    }

                    ChatMessageDTO ultimoMensaje = new ChatMessageDTO();
                    ultimoMensaje.setEmisorId(emisorId);
                    ultimoMensaje.setReceptorId(receptorId);
                    ultimoMensaje.setContenido(contenido);
                    ultimoMensaje.setConversationId(conversationId);
                    ultimoMensaje.setFecha(fecha.toString());

                    ConversacionDTO conv = new ConversacionDTO();
                    conv.setConversationId(conversationId);
                    conv.setEmisorId(emisorId);
                    conv.setReceptorId(receptorId);
                    conv.setEmisorName(emisorName);
                    conv.setReceptorName(receptorName);
                    conv.setUltimoMensaje(ultimoMensaje);

                    int noLeidos = mensajeRepository.countUnread(conversationId, userId);
                    conv.setUnreadCount(noLeidos);

                    conv.setEmisorImageUrl(userRepository.findById(emisorId)
                            .map(u -> u.getImageUrl())
                            .orElse(null));
                    conv.setReceptorImageUrl(userRepository.findById(receptorId)
                            .map(u -> u.getImageUrl())
                            .orElse(null));

                    return conv;
                })
                .collect(Collectors.toList());
    }

    private String normalizarConversation(Long a, Long b) {
        return (a < b) ? a + "-" + b : b + "-" + a;
    }

    private String normalizarConversation(String id) {
        try {
            String[] parts = id.split("-");
            Long a = Long.valueOf(parts[0]);
            Long b = Long.valueOf(parts[1]);
            return normalizarConversation(a, b);
        } catch (Exception e) {
            return id;
        }
    }

    @Transactional
    public void marcarMensajesComoLeidos(String conversationId, Long userId) {
        List<Mensaje> mensajes = mensajeRepository.findByConversationIdOrderByFechaAsc(conversationId);
        for (Mensaje m : mensajes) {
            if (m.getReceptorId().equals(userId) && (m.getLeido() == null || !m.getLeido())) {
                m.setLeido(true);
            }
        }
        mensajeRepository.saveAll(mensajes);
    }
}
