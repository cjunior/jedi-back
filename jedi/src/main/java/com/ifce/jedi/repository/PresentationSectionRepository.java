package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.PresentationSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PresentationSectionRepository extends JpaRepository<PresentationSection, Long> {
    Optional<PresentationSection> findFirstByOrderByIdAsc();
}