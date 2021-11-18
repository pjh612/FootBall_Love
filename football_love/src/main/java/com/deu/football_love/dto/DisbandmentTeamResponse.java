package com.deu.football_love.dto;

import lombok.Getter;

@Getter
public class DisbandmentTeamResponse {
    private String teamName;

    public DisbandmentTeamResponse(String teamName) {
        this.teamName = teamName;
    }
}
