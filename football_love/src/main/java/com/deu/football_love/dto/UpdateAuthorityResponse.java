package com.deu.football_love.dto;

import com.deu.football_love.domain.type.AuthorityType;

public class UpdateAuthorityResponse {
    private String teamName;
    private String memberId;
    private AuthorityType authorityType;

    public UpdateAuthorityResponse(String teamName, String memberId, AuthorityType authorityType) {
        this.teamName = teamName;
        this.memberId = memberId;
        this.authorityType = authorityType;
    }
}
