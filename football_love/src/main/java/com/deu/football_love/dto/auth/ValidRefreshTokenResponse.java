package com.deu.football_love.dto.auth;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ValidRefreshTokenResponse {
    private String userPk;
    private int status;
    private String accessToken;

    public ValidRefreshTokenResponse(String userPk, int status, String accessToken) {
        this.userPk = userPk;
        this.status = status;
        this.accessToken = accessToken;
    }
}

