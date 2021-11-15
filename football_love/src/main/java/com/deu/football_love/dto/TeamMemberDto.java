package com.deu.football_love.dto;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.AuthorityType;
import lombok.Getter;

@Getter
public class TeamMemberDto {

    private Long id;

    private String teamName;

    private String memberId;

    private AuthorityType authority;

    public TeamMemberDto(TeamMember teamMember) {
        this.id = teamMember.getId();
        this.teamName = teamMember.getTeam().getName();
        this.memberId = teamMember.getMember().getId();
        this.authority = teamMember.getAuthority();
    }
}
