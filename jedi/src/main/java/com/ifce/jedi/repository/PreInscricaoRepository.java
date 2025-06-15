package com.ifce.jedi.repository;

import com.ifce.jedi.model.User.PreInscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PreInscricaoRepository extends JpaRepository<PreInscricao, UUID> {
    Optional<PreInscricao> findByEmail(String email);

    Optional<PreInscricao> findByContinuationToken(String token);
}
