package com.deu.football_love.dto.team;

import lombok.Getter;

@Getter
public class CreateTeamResponse {
    private Long teamId;
    private String teamName;

    public CreateTeamResponse(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
