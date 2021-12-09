package com.deu.football_love.service;

import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.stadium.RemoveStadiumResponse;
import com.deu.football_love.dto.stadium.QueryStadiumDto;

import java.util.List;

public interface StadiumService {
    QueryStadiumDto findStadium(Long stadiumId);

    List<QueryStadiumDto> findAllStadiumByCompanyId(Long companyId);

    AddStadiumResponse addStadium(Long companyId, String type, String size, Long cost);

    RemoveStadiumResponse deleteStadium(Long stadiumId);
}
