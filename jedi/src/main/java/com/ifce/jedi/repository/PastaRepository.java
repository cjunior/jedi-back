package com.ifce.jedi.repository;

import com.ifce.jedi.model.Arquivos.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PastaRepository extends JpaRepository<Pasta, Long> {
    List<Pasta> findByParent(Pasta parent);
    List<Pasta> findByParentIsNull();

    Optional<Pasta> findByParentAndSlug(Pasta parent, String slug);
    Optional<Pasta> findByParentIsNullAndSlug(String slug);

    boolean existsByParentAndSlug(Pasta parent, String slug);
    boolean existsByParentIsNullAndSlug(String slug);
    boolean existsByParentAndSlugAndIdNot(Pasta parent, String slug, Long id);
    boolean existsByParentIsNullAndSlugAndIdNot(String slug, Long id);
}
