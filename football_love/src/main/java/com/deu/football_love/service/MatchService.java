package com.deu.football_love.service;

import com.deu.football_love.dto.match.CreateMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;

import java.time.LocalDateTime;

public interface MatchService {
    QueryMatchDto findMatch(Long matchId);

    CreateMatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime);
}
