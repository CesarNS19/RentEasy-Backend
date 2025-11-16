package com.utsem.app.repository;

import com.utsem.app.model.Conversacion;
import com.utsem.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {
    Optional<Conversacion> findByUsuario1AndUsuario2(User u1, User u2);
    Optional<Conversacion> findByUsuario2AndUsuario1(User u1, User u2);
}
