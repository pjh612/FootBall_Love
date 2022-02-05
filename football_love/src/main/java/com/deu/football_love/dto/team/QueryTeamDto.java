package com.deu.football_love.dto.team;

import com.deu.football_love.domain.*;
import com.deu.football_love.dto.BaseDto;
import com.deu.football_love.dto.board.BoardDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QueryTeamDto extends BaseDto {

    private Long id;
    private String name;
    private List<String> teamMembers = new ArrayList<>();
    private List<BoardDto> boards = new ArrayList<>();
    public QueryTeamDto(Long id, String name, List<TeamMember> teamMembers) {
        this.id = id;
        this.name = name;
        teamMembers.forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
    }

    public QueryTeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        team.getTeamMembers().forEach(teamMember ->{this.teamMembers.add(teamMember.getMember().getId());});
        boards = team.getBoards().stream().map(board -> new BoardDto(board)).collect(Collectors.toList());
     /*   setCreatedDate(team.getCreatedDate());
        setLastModifiedDate(team.getLastModifiedDate());
        setCreatedBy(team.getCreatedBy());
        setLastModifiedBy(team.getLastModifiedBy());*/
    }

    public static QueryTeamDto from(Team team)
    {
        return new QueryTeamDto(team);
    }


}
