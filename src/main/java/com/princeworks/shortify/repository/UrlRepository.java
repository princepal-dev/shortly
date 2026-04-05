package com.princeworks.shortify.repository;

import com.princeworks.shortify.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
  Optional<Url> findByShortCode(String shortCode);

  Page<Url> findByUserId(Long userId, Pageable pageable);

  @Modifying
  @Query("UPDATE Url u SET u.clickCount = u.clickCount + 1 WHERE u.shortCode = :click_count")
  void incrementClickCount(@Param("click_count") String code);
}
