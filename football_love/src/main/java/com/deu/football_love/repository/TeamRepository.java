package com.deu.football_love.repository;

import com.deu.football_love.domain.ApplicationJoinTeam;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamMember;

import java.util.List;

public interface TeamRepository {

    void insertTeam(Team team);
    Team selectTeam(Long teamId);
    Team selectTeamByName(String teamName);
    void insertNewApplication(ApplicationJoinTeam application);
    TeamMember insertNewTeamMember(TeamMember newTeamMember);
    ApplicationJoinTeam selectApplication(Long teamId, String memberId);
    void deleteApplication(ApplicationJoinTeam application);
    void deleteTeamMember(Long teamId, Long memberNumber);
    List<TeamMember> selectTeamMember(Long teamId, Long memberNumber);
    void deleteTeam(Team team);
    boolean existsTeamByTeamName(String teamName);
}
