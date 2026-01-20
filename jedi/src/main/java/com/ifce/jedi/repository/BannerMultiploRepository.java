package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.BannerMultiplo.BannerMultiplo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerMultiploRepository extends JpaRepository<BannerMultiplo, Long> {
    @Query("select max(b.position) from BannerMultiplo b")
    Integer findMaxPosition();
}
