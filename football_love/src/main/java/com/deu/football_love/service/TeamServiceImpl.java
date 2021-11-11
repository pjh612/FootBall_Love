package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.TeamDto;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    public TeamDto getTeamInfo(Team team)
    {
        return TeamDto.from(team);
    }

    @Override
    public void createNewTeam(Team team) {
        teamRepository.insertTeam(team);
    }

    @Override
    public Team findTeam(String teamName)
    {
        return teamRepository.selectTeam(teamName);
    }

    @Override
    public ApplicationJoinTeam findApplication(String teamName, String memberId) {
        return teamRepository.selectApplication(teamName, memberId);
    }
    @Override
    public void applyToTeam(ApplicationJoinTeam application) {
        teamRepository.insertNewApplication(application);
    }

    @Override
    public void acceptApplication(ApplicationJoinTeam application, TeamMember newTeamMember) {
        teamRepository.deleteApplication(application);
        teamRepository.insertNewTeamMember(newTeamMember);
    }

    /**
     *  팀에 속해 있는지, 속해 있다면 일반 회원인지 관리자인지 파악
     * 
     */
    @Override
    public AuthorityType authorityCheck(String teamName, String memberId)
    {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamName, memberId);

        if(teamMembers.size() == 0)
            return AuthorityType.NONE;
        return teamMembers.get(0).getAuthority();
    }

    @Override
    public List<TeamMember> findTeamMember(String teamName, String memberId)
    {
        return teamRepository.selectTeamMember(teamName, memberId);
    }
    @Override
    public void withdrawal(String teamName, String memberId)
    {
        teamRepository.deleteTeamMember(teamName, memberId);
    }

    @Override
    public void disbandmentTeam(Team team)
    {
        teamRepository.deleteTeam(team);
    }

}
