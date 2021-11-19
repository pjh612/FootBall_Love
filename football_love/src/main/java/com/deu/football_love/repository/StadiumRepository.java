package com.deu.football_love.repository;

import com.deu.football_love.domain.Stadium;

public interface StadiumRepository {
    Stadium selectStadium(Long stadiumId);
    void insertStadium(Stadium stadium);
    void deleteStadium(Stadium stadium);
}
