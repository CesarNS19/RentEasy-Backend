package com.utsem.app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "conversaciones")
public class Conversacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario1_id", nullable = false)
	private User usuario1;

	@ManyToOne
	@JoinColumn(name = "usuario2_id", nullable = false)
	private User usuario2;

	@OneToMany(mappedBy = "conversacion", cascade = CascadeType.ALL)
	private List<Mensaje> mensajes;

	public Conversacion() {
	}

	public Conversacion(User usuario1, User usuario2) {
		this.usuario1 = usuario1;
		this.usuario2 = usuario2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUsuario1() {
		return usuario1;
	}

	public void setUsuario1(User usuario1) {
		this.usuario1 = usuario1;
	}

	public User getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(User usuario2) {
		this.usuario2 = usuario2;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
}
