package com.utsem.app.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void enviarNotificacion(String destinatario, String mensaje) {
        System.out.println("📱 Notificación para " + destinatario + ": " + mensaje);
    }
}
