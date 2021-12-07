package com.deu.football_love.repository;

import com.deu.football_love.domain.Stadium;

import java.util.List;

public interface StadiumRepository {
    Stadium selectStadium(Long stadiumId);

    List<Stadium> selectAllStadiumByCompanyId(Long companyId);

    void insertStadium(Stadium stadium);
    void deleteStadium(Stadium stadium);
}
