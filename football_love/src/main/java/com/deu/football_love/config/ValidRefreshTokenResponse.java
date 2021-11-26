package com.deu.football_love.config;

public class ValidRefreshTokenResponse {
    String userPk;
    int status;
    String accessKey;

    public ValidRefreshTokenResponse(String userPk, int status, String accessKey) {
        this.userPk = userPk;
        this.status = status;
        this.accessKey = accessKey;
    }
}

