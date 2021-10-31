package com.deu.football_love.repository;

import com.deu.football_love.domain.ApplicationJoinTeam;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamAdmin;
import com.deu.football_love.domain.TeamMember;

import java.util.List;

public interface TeamRepository {

    void insertTeam(TeamAdmin teamAdmin, Team team);
    Team selectTeam(String teamName);
    void insertNewApplication(ApplicationJoinTeam application);
    void insertNewTeamMember(TeamMember newTeamMember);
    ApplicationJoinTeam selectApplication(String teamName, String memberId);
    void deleteApplication(ApplicationJoinTeam application);
    void deleteTeamMember(String teamName, String memberId);
    List<TeamMember> selectTeamMember(String teamName, String memberId);
    List<TeamAdmin> selectTeamAdmin(String teamName, String memberId);
    void deleteTeam(Team team);
}
