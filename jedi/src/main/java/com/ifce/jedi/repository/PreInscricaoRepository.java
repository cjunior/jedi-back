package com.ifce.jedi.repository;

import com.ifce.jedi.model.User.PreInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface PreInscricaoRepository extends JpaRepository<PreInscricao, UUID>, JpaSpecificationExecutor<PreInscricao>  {
    Optional<PreInscricao> findByEmail(String email);

    Optional<PreInscricao> findByContinuationToken(String token);
}
