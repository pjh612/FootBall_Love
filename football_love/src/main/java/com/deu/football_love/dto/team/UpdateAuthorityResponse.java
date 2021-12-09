package com.deu.football_love.dto.team;

import com.deu.football_love.domain.type.AuthorityType;

public class UpdateAuthorityResponse {
    private Long teamId;
    private String memberId;
    private AuthorityType authorityType;

    public UpdateAuthorityResponse(Long teamId, String memberId, AuthorityType authorityType) {
        this.teamId = teamId;
        this.memberId = memberId;
        this.authorityType = authorityType;
    }
}
