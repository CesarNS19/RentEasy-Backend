package com.utsem.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long emisorId;
    private Long receptorId;
    private String contenido;
    private LocalDateTime fecha;

    private String conversationId;

    public Mensaje() { }

    public Mensaje(Long emisorId, Long receptorId, String contenido, LocalDateTime fecha, String conversationId) {
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.contenido = contenido;
        this.fecha = fecha;
        this.conversationId = conversationId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmisorId() {
		return emisorId;
	}

	public void setEmisorId(Long emisorId) {
		this.emisorId = emisorId;
	}

	public Long getReceptorId() {
		return receptorId;
	}

	public void setReceptorId(Long receptorId) {
		this.receptorId = receptorId;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
}
