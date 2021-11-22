package com.deu.football_love.dto;

import com.deu.football_love.domain.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamDto {

    private Long id;
    private String name;

    private LocalDate createDate;

    private List<String> teamMembers = new ArrayList<>();

    public TeamDto(Long id, String name, LocalDate createDate, List<TeamMember> teamMembers) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        teamMembers.forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
    }

    public static TeamDto from(Team team)
    {
        return new TeamDto(team.getId(), team.getName(),team.getCreateDate(),team.getTeamMembers());
    }


}
