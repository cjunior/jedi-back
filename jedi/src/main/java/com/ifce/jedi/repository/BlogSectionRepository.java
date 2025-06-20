package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.BlogSection.BlogSection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlogSectionRepository extends JpaRepository<BlogSection, Long> {
    Optional<BlogSection> findFirstByOrderByIdAsc();
}