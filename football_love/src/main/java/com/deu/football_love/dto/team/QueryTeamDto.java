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

    public QueryTeamDto(Long id, String name, LocalDateTime createDate, List<TeamMember> teamMembers) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        teamMembers.forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
    }

    public QueryTeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.createDate = team.getCreateDate();
        team.getTeamMembers().forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
      /*  setCreatedDate(team.getCreatedDate());
        setLastModifiedDate(team.getLastModifiedDate());
        setCreatedBy(team.getCreatedBy());
        setLastModifiedBy(team.getLastModifiedBy());*/
    }

    public static QueryTeamDto from(Team team)
    {
        return new QueryTeamDto(team.getId(), team.getName(),team.getCreateDate(),team.getTeamMembers());
    }


}
