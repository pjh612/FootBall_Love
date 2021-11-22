package com.deu.football_love.service;

import com.deu.football_love.dto.AddStadiumResponse;
import com.deu.football_love.dto.RemoveStadiumResponse;
import com.deu.football_love.dto.StadiumDto;

public interface StadiumService {
    StadiumDto findStadium(Long stadiumId);
    AddStadiumResponse addStadium(Long companyId, String type, String size, Long cost);
    RemoveStadiumResponse removeStadium(Long stadiumId);
}
