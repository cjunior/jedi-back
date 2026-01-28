package com.ifce.jedi.repository;

import com.ifce.jedi.model.Arquivos.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PastaRepository extends JpaRepository<Pasta, Long> {
    List<Pasta> findByParent(Pasta parent);
    List<Pasta> findByParentIsNull();
}
