package com.utsem.app.repository;

import com.utsem.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    @Query(value = "SELECT m.conversation_id, " +
            "m.emisor_id, m.receptor_id, " +
            "u1.username as emisor_name, u1.image_url as emisor_image, " +
            "u2.username as receptor_name, u2.image_url as receptor_image, " +
            "m.contenido, m.fecha " +
            "FROM mensajes m " +
            "INNER JOIN ( " +
            "    SELECT conversation_id, MAX(fecha) as ultima_fecha " +
            "    FROM mensajes " +
            "    WHERE emisor_id = :userId OR receptor_id = :userId " +
            "    GROUP BY conversation_id " +
            ") ultimos ON m.conversation_id = ultimos.conversation_id AND m.fecha = ultimos.ultima_fecha " +
            "INNER JOIN users u1 ON m.emisor_id = u1.id " +
            "INNER JOIN users u2 ON m.receptor_id = u2.id", 
    nativeQuery = true)
    List<Object[]> findLastMessages(@Param("userId") Long userId);
}
