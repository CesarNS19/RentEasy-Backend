package com.utsem.app.repository;

import com.utsem.app.model.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query("SELECT AVG(c.estrellas) FROM Comentario c WHERE c.propiedad.id = :id")
    Double promedioPorPropiedad(@Param("id") Long id);
    
    List<Comentario> findByPropiedadId(Long propiedadId);
}
