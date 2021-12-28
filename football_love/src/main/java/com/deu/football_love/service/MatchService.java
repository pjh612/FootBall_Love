package com.deu.football_love.service;

import java.time.LocalDateTime;

import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;

public interface MatchService {
	QueryMatchDto findMatch(Long matchId);

	AddMatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime);

	MatchApproveResponse approveMatch(Long matchId, Long matchApplicationId);

	MatchApplicationResponse addMatchApplication(Long teamId, Long matchId);

	void cancelMatch(Long matchId);

	ModifyMatchResponse modifyMatch(Long matchId, Long stadiumId, LocalDateTime reservationTime);
}
