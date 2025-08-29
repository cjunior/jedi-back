package com.ifce.jedi.repository;

import com.ifce.jedi.model.Ciclo.Ciclo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CicloRepository extends JpaRepository<Ciclo, UUID> {
    List<Ciclo> findByDataInicioBeforeAndDataFimAfter(LocalDateTime dataInicio, LocalDateTime dataFim);
}
