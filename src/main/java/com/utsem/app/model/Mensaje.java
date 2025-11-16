package com.utsem.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "emisor_id", nullable = false)
	private User emisor;

	@ManyToOne
	@JoinColumn(name = "receptor_id", nullable = false)
	private User receptor;

	@Column(nullable = false)
	private String contenido;

	private LocalDateTime fechaEnvio = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "conversacion_id")
	private Conversacion conversacion;

	public Mensaje() {
	}

	public Mensaje(User emisor, User receptor, String contenido, Conversacion conversacion) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.contenido = contenido;
		this.conversacion = conversacion;
		this.fechaEnvio = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getEmisor() {
		return emisor;
	}

	public void setEmisor(User emisor) {
		this.emisor = emisor;
	}

	public User getReceptor() {
		return receptor;
	}

	public void setReceptor(User receptor) {
		this.receptor = receptor;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Conversacion getConversacion() {
		return conversacion;
	}

	public void setConversacion(Conversacion conversacion) {
		this.conversacion = conversacion;
	}
}
