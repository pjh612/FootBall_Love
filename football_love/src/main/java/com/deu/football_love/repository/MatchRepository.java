package com.deu.football_love.repository;

import com.deu.football_love.domain.Matches;

public interface MatchRepository {
    Matches selectMatch(Long id);
    void insertMatch(Matches match);
}
