package com.deu.football_love.service;

import java.time.LocalDateTime;

import com.deu.football_love.dto.match.MatchResponse;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;

public interface MatchService {
	QueryMatchDto findMatch(Long matchId);

	MatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime);

	MatchResponse approveMatch(Long matchId, Long otherTeamId);

	MatchResponse addMatchApplication(Long teamId, Long matchId);

	void cancelMatch(Long matchId);

	ModifyMatchResponse modifyMatch(Long matchId, Long stadiumId, LocalDateTime reservationTime);
}
