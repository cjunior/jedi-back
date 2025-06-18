package com.ifce.jedi.repository;

import com.ifce.jedi.model.SecoesSite.Banner.BannerItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerItemRepository extends JpaRepository<BannerItem, Long> {
}
