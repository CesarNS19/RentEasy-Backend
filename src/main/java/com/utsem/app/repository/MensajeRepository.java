package com.utsem.app.repository;

import com.utsem.app.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByConversationIdOrderByFechaAsc(String conversationId);
    
    @Query(value = "SELECT m.conversation_id, m.contenido, m.fecha, m.receptor_id, m.emisor_id " +
    	       "FROM mensajes m WHERE m.conversation_id IN " +
    	       "(SELECT conversation_id FROM mensajes WHERE emisor_id = :userId OR receptor_id = :userId) " +
    	       "ORDER BY m.fecha DESC LIMIT 1", nativeQuery = true)
    	List<Object[]> findLastMessages(@Param("userId") Long userId);
}
