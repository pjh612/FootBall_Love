package com.deu.football_love.dto.stadium;

import lombok.Getter;

public class RemoveStadiumResponse {
    private Long stadiumId;

    public RemoveStadiumResponse(Long stadiumId) {
        this.stadiumId = stadiumId;
    }
}
