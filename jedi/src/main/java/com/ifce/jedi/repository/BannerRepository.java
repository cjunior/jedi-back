package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Banner.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
