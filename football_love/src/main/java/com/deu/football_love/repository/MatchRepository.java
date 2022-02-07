package com.deu.football_love.repository;

import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Matches, Long> {

//	Matches updateMatch(Long matchId);

//	void insertMatchApplication(MatchApplication matchApplication);

//	MatchApplication updateMatchApplication(Long matchApplicationId);

//	Matches updateMatch(Long matchId, Stadium stadium, LocalDateTime reservationTime);
}
