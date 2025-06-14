package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Banner;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
