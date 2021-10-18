package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamAdmin;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    @Override
    public void createNewTeam(TeamAdmin admin, Team newTeam) {
        teamRepository.insertTeam(admin,newTeam);
    }

    public Team findTeam(String teamName)
    {
        return teamRepository.selectTeamByName(teamName);
    }

    public void joinTeam(TeamMember newTeamMember)
    {
        teamRepository.insertNewTeamMember(newTeamMember);
    }
}
