package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Rede.RedeJediImage;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedeJediImageRepository extends JpaRepository<RedeJediImage, Long> {
    List<RedeJediImage> findBySection(RedeJediSection section);
}

