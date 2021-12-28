package com.deu.football_love.dto.team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTeamRequest {
    private String name;

    public CreateTeamRequest(String name) {
        this.name = name;
    }
}
