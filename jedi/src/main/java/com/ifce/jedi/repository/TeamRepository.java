package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
