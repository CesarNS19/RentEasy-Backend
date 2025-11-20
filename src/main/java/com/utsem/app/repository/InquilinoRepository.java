package com.utsem.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utsem.app.model.Inquilino;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
    Optional<Inquilino> findByUsername(String username);
}
