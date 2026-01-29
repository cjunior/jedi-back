package com.ifce.jedi.repository;

import com.ifce.jedi.model.Arquivos.Arquivo;
import com.ifce.jedi.model.Arquivos.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    List<Arquivo> findByPasta(Pasta pasta);
    void deleteByPasta(Pasta pasta);
}
