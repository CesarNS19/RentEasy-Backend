package com.utsem.app.repository;

import com.utsem.app.model.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
    List<Propiedad> findByPropietarioId(Long propietarioId);

    @Query("SELECT p FROM Propiedad p WHERE " +
           "(:ubicacion IS NULL OR p.ubicacion LIKE %:ubicacion%) AND " +
           "(:tipo IS NULL OR p.tipo = :tipo) AND " +
           "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR p.precio <= :precioMax)")
    List<Propiedad> buscarFiltrado(@Param("ubicacion") String ubicacion,
                                   @Param("tipo") String tipo,
                                   @Param("precioMin") Double precioMin,
                                   @Param("precioMax") Double precioMax);
}

