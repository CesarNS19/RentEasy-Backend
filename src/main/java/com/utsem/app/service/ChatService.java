package com.utsem.app.service;

import com.utsem.app.dto.MensajeDTO;
import com.utsem.app.model.*;
import com.utsem.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Mensaje> obtenerMensajes(Long conversacionId) {
        Conversacion conversacion = conversacionRepository.findById(conversacionId)
                .orElseThrow(() -> new RuntimeException("Conversación no encontrada"));
        return mensajeRepository.findByConversacionOrderByFechaEnvioAsc(conversacion);
    }

    public Conversacion iniciarConversacion(User emisor, User receptor) {
        return conversacionRepository
                .findByUsuario1AndUsuario2(emisor, receptor)
                .orElseGet(() -> conversacionRepository
                        .findByUsuario2AndUsuario1(emisor, receptor)
                        .orElseGet(() -> conversacionRepository.save(new Conversacion(emisor, receptor))));
    }

    public Mensaje enviarMensaje(MensajeDTO dto, User emisor, User receptor) {
        Conversacion conversacion = iniciarConversacion(emisor, receptor);
        Mensaje mensaje = new Mensaje(emisor, receptor, dto.getContenido(), conversacion);
        mensajeRepository.save(mensaje);

        notificationService.enviarNotificacion(receptor.getUsername(), "Nuevo mensaje de " + emisor.getUsername());

        return mensaje;
    }
}
