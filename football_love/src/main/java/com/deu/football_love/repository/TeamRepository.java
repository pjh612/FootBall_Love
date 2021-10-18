package com.deu.football_love.repository;

import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamAdmin;
import com.deu.football_love.domain.TeamMember;

public interface TeamRepository {

    void insertTeam(TeamAdmin teamAdmin, Team team);
    Team selectTeam(Long id);
    Team selectTeamByName(String teamName);
    void insertNewTeamMember(TeamMember newTeamMember);
}
