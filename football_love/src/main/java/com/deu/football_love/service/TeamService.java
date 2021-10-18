package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamAdmin;
import com.deu.football_love.domain.TeamMember;

public interface TeamService {

    void createNewTeam(TeamAdmin teamAdmin, Team newTeam);
    Team findTeam(String teamName);
    void joinTeam(TeamMember newTeamMember);
}
