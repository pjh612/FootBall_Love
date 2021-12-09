package com.deu.football_love.dto.team;

import com.deu.football_love.domain.*;
import com.deu.football_love.dto.BaseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class QueryTeamDto extends BaseDto {

    private Long id;
    private String name;

    private LocalDateTime createDate;

    private List<String> teamMembers = new ArrayList<>();

    public QueryTeamDto(Long id, String name, List<TeamMember> teamMembers) {
        this.id = id;
        this.name = name;
        teamMembers.forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
    }

    public QueryTeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        team.getTeamMembers().forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
      /*  setCreatedDate(team.getCreatedDate());
        setLastModifiedDate(team.getLastModifiedDate());
        setCreatedBy(team.getCreatedBy());
        setLastModifiedBy(team.getLastModifiedBy());*/
    }

    public static QueryTeamDto from(Team team)
    {
        return new QueryTeamDto(team.getId(), team.getName(),team.getTeamMembers());
    }


}
