package com.deu.football_love.repository;

import java.time.LocalDateTime;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;

public interface MatchRepository {
	Matches selectMatch(Long id);

	void insertMatch(Matches match);

	Matches updateMatch(Long matchId);

	void insertMatchApplication(MatchApplication matchApplication);

	void deleteMatch(Matches match);

	MatchApplication updateMatchApplication(Long matchApplicationId);

	Matches updateMatch(Long matchId, Stadium stadium, LocalDateTime reservationTime);
}
