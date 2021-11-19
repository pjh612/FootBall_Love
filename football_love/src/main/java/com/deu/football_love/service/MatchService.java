package com.deu.football_love.service;

import com.deu.football_love.dto.CreateMatchResponse;
import com.deu.football_love.dto.MatchDto;

import java.time.LocalDateTime;

public interface MatchService {
    MatchDto findMatch(Long matchId);
    CreateMatchResponse addMatch(Long teamId, Long stadiumId, LocalDateTime reservationTime);
}
