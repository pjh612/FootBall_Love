package com.deu.football_love.dto.team;

import com.deu.football_love.domain.type.TeamMemberType;

public class UpdateAuthorityResponse {
    private Long teamId;
    private String memberId;
    private TeamMemberType authorityType;

    public UpdateAuthorityResponse(Long teamId, String memberId, TeamMemberType authorityType) {
        this.teamId = teamId;
        this.memberId = memberId;
        this.authorityType = authorityType;
    }
}
