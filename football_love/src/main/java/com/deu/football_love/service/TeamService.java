package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.TeamDto;

import java.util.List;

public interface TeamService {

    TeamDto getTeamInfo(Team team);
    void createNewTeam(Team team);
    Team findTeam(String teamName);
    ApplicationJoinTeam findApplication(String teamName, String memberId);
    void applyToTeam(ApplicationJoinTeam application);
    void acceptApplication(ApplicationJoinTeam application, TeamMember newTeamMember);
    AuthorityType authorityCheck(String teamName, String memberId);
    void withdrawal(String teamName, String memberId);
    void disbandmentTeam(Team team);
    List<TeamMember> findTeamMember(String teamName, String memberId);
    void updateAuthority(TeamMember member, AuthorityType authorityType);
}
