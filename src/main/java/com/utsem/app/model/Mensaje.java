package com.utsem.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long emisorId;
    private Long receptorId;
    private String contenido;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime fecha;

    private String conversationId;

    @Column(nullable = false)
    private Boolean leido = false;

    public Mensaje() { }

    public Mensaje(Long emisorId, Long receptorId, String contenido, LocalDateTime fecha, String conversationId) {
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.contenido = contenido;
        this.fecha = fecha;
        this.conversationId = conversationId;
        this.leido = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmisorId() { return emisorId; }
    public void setEmisorId(Long emisorId) { this.emisorId = emisorId; }

    public Long getReceptorId() { return receptorId; }
    public void setReceptorId(Long receptorId) { this.receptorId = receptorId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }

    @Converter(autoApply = false)
    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

        @Override
        public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
            return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
            return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
        }
    }
}
