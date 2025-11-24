package com.utsem.app.dto;

public class ConversacionDTO {
    private String conversationId;
    private Long emisorId;
    private Long receptorId;
    private String emisorName;
    private String receptorName;
    private ChatMessageDTO ultimoMensaje;
    private Integer unreadCount;

    private String emisorImageUrl;
    private String receptorImageUrl;

    // Getters y Setters
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public Long getEmisorId() { return emisorId; }
    public void setEmisorId(Long emisorId) { this.emisorId = emisorId; }

    public Long getReceptorId() { return receptorId; }
    public void setReceptorId(Long receptorId) { this.receptorId = receptorId; }

    public String getEmisorName() { return emisorName; }
    public void setEmisorName(String emisorName) { this.emisorName = emisorName; }

    public String getReceptorName() { return receptorName; }
    public void setReceptorName(String receptorName) { this.receptorName = receptorName; }

    public ChatMessageDTO getUltimoMensaje() { return ultimoMensaje; }
    public void setUltimoMensaje(ChatMessageDTO ultimoMensaje) { this.ultimoMensaje = ultimoMensaje; }

    public Integer getUnreadCount() { return unreadCount; }
    public void setUnreadCount(Integer unreadCount) { this.unreadCount = unreadCount; }

    public String getEmisorImageUrl() { return emisorImageUrl; }
    public void setEmisorImageUrl(String emisorImageUrl) { this.emisorImageUrl = emisorImageUrl; }

    public String getReceptorImageUrl() { return receptorImageUrl; }
    public void setReceptorImageUrl(String receptorImageUrl) { this.receptorImageUrl = receptorImageUrl; }
}
