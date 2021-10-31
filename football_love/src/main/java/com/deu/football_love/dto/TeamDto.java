package com.deu.football_love.dto;

import com.deu.football_love.domain.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamDto {

    private String name;

    private LocalDate createDate;

    private List<String> teamMembers = new ArrayList<>();

    public TeamDto(String name, LocalDate createDate, List<TeamMember> teamMembers) {
        this.name = name;
        this.createDate = createDate;
        teamMembers.forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
    }

    public static TeamDto from(Team team)
    {
        return new TeamDto(team.getName(),team.getCreateDate(),team.getTeamMembers());
    }


}
