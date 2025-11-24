package com.utsem.app.dto;

import java.time.LocalDateTime;

public class ComentarioDTO {
    private String mensaje;
    private int estrellas;
    private Long propiedadId;
    private Long usuarioId;
    private String username;
    private LocalDateTime fecha;

    private String imageUrl;

    public ComentarioDTO(String mensaje, int estrellas, Long propiedadId, Long usuarioId, String username, LocalDateTime fecha, String imageUrl) {
        this.mensaje = mensaje;
        this.estrellas = estrellas;
        this.propiedadId = propiedadId;
        this.usuarioId = usuarioId;
        this.username = username;
        this.fecha = fecha;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
    
}
