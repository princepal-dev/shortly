package com.princeworks.shortify.repository;

import com.princeworks.shortify.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {}
