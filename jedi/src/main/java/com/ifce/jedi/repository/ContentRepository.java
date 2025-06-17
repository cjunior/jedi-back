package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Contents.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
