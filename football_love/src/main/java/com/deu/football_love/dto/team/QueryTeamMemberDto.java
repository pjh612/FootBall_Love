package com.deu.football_love.dto.team;

import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.TeamMemberType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryTeamMemberDto {

  private Long teamMemberId;

  private Long teamId;

  private String teamName;

  private String memberId;

  private TeamMemberType authority;

  public QueryTeamMemberDto(TeamMember teamMember) {
    this.teamMemberId = teamMember.getId();
    this.teamId = teamMember.getTeam().getId();
    this.teamName = teamMember.getTeam().getName();
    this.memberId = teamMember.getMember().getId();
    this.authority = teamMember.getType();
  }
}
