package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Header.Header;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HeaderRepository extends JpaRepository<Header, UUID> {

}
