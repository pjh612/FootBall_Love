package com.deu.football_love.repository;

import com.deu.football_love.domain.MatchApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long> {
}
