package com.utsem.app.repository;

import com.utsem.app.model.Conversacion;
import com.utsem.app.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByConversacionOrderByFechaEnvioAsc(Conversacion conversacion);
}
