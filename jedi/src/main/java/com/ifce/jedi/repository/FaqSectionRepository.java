package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.FaqSection.FaqSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqSectionRepository extends JpaRepository<FaqSection, Long> {
    Optional<FaqSection> findFirstByOrderByIdAsc();
}