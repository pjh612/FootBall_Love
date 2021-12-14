package com.deu.football_love.dto.team;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.TeamMemberType;
import lombok.Getter;

@Getter
public class QueryTeamMemberDto {

    private Long id;

    private String teamName;

    private String memberId;

    private TeamMemberType authority;

    public QueryTeamMemberDto(TeamMember teamMember) {
        this.id = teamMember.getId();
        this.teamName = teamMember.getTeam().getName();
        this.memberId = teamMember.getMember().getId();
        this.authority = teamMember.getType();
    }
}
