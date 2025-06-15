package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.PresentationSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PresentationSectionRepository extends JpaRepository<PresentationSection, UUID> {
}
