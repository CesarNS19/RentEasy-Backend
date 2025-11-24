package com.utsem.app.repository;

import com.utsem.app.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByConversationIdOrderByFechaAsc(String conversationId);

    @Query(value = "SELECT m.conversation_id, " +
                   "m.emisor_id, m.receptor_id, " +
                   "(SELECT u.username FROM users u WHERE u.id = m.emisor_id) as emisor_name, " +
                   "(SELECT u.username FROM users u WHERE u.id = m.receptor_id) as receptor_name, " +
                   "m.contenido, m.fecha " +
                   "FROM mensajes m " +
                   "INNER JOIN ( " +
                   "    SELECT conversation_id, MAX(fecha) as ultima_fecha " +
                   "    FROM mensajes " +
                   "    WHERE emisor_id = :userId OR receptor_id = :userId " +
                   "    GROUP BY conversation_id " +
                   ") ultimos ON m.conversation_id = ultimos.conversation_id AND m.fecha = ultimos.ultima_fecha", 
           nativeQuery = true)
    List<Object[]> findLastMessages(@Param("userId") Long userId);

    @Query(value = "SELECT COUNT(*) FROM mensajes " +
                   "WHERE conversation_id = :conversationId AND receptor_id = :userId AND leido = false",
           nativeQuery = true)
    int countUnread(@Param("conversationId") String conversationId, @Param("userId") Long userId);
}
