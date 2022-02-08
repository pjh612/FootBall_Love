package com.deu.football_love.dto.team;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.TeamMemberType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryTeamListItemDto {
    private Long teamId;
    private String teamName;
    private TeamMemberType authority;
    private Long totalMemberCount;
    private String profileImgUri;

    public QueryTeamListItemDto(TeamMember teamMember) {
        this.teamId = teamMember.getTeam().getId();
        this.teamName = teamMember.getTeam().getName();
        this.authority = teamMember.getType();
    //    this.totalMemberCount = Long.valueOf(teamMember.getTeam().getTeamMembers().size());
    }

    public QueryTeamListItemDto(Long teamId, String teamName, TeamMemberType authority, int totalMemberCount, String profileImgUri) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.authority = authority;
        this.totalMemberCount = Long.valueOf(totalMemberCount);
        this.profileImgUri = profileImgUri;
    }

    public static QueryTeamListItemDto from(TeamMember teamMember)
    {
        return new QueryTeamListItemDto(teamMember);
    }
}
