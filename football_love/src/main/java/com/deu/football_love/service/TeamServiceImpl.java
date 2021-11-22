package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.*;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public TeamDto getTeamInfo(Long teamId)
    {
        Team team = teamRepository.selectTeam(teamId);
        if (team == null)
            return null;
        return TeamDto.from(team);
    }

    @Override
    @Transactional
    public CreateTeamResponse createNewTeam(String creator, String teamName) {
        Team team = new Team();
        Member findMember = memberRepository.selectMember(creator);
        if (findMember == null)
            return null;
        TeamMember teamMember = new TeamMember(team, findMember, AuthorityType.LEADER);
        team.setName(teamName);
        team.setCreateDate(LocalDate.now());
        teamMember.setTeam(team);
        teamMember.setMember(findMember);
        team.getTeamMembers().add(teamMember);
        teamRepository.insertTeam(team);
        teamRepository.insertNewTeamMember(teamMember);
        return new CreateTeamResponse(teamMember.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDto findTeam(Long teamId)
    {
        Team team = teamRepository.selectTeam(teamId);
        if (team == null)
            return null;
        return TeamDto.from(team);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDto findTeamByName(String teamName)
    {
        Team team = teamRepository.selectTeamByName(teamName);
        if (team == null)
            return null;
        return TeamDto.from(team);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationJoinTeamDto findApplication(Long teamId, String memberId) {
        ApplicationJoinTeam application = teamRepository.selectApplication(teamId, memberId);
        if (application == null)
            return null;
        return new ApplicationJoinTeamDto(application);
    }

    @Override
    public void applyToTeam(Long teamId, String memberId, String message) {

        Team findTeam = teamRepository.selectTeam(teamId);
        Member findMember = memberRepository.selectMember(memberId);
        ApplicationJoinTeam application = new ApplicationJoinTeam(findTeam, findMember, message);
        findTeam.getApplicationJoinTeams().add(application);
        teamRepository.insertNewApplication(application);
    }

    @Override
    @Transactional
    public AcceptApplicationResponse acceptApplication(Long teamId, String memberId) {
        ApplicationJoinTeam findApplication = teamRepository.selectApplication(teamId, memberId);
        Team findTeam = teamRepository.selectTeam(teamId);
        Member findMember = memberRepository.selectMember(memberId);
        if (findApplication == null || findTeam == null || findMember == null)
            return null;
        teamRepository.deleteApplication(findApplication);
        TeamMember newTeamMember = new TeamMember(findTeam, findMember, AuthorityType.MEMBER);
        findTeam.getTeamMembers().add(newTeamMember);
        teamRepository.insertNewTeamMember(newTeamMember);
        return new AcceptApplicationResponse(teamId, memberId);
    }

    /**
     *  팀에 속해 있는지, 속해 있다면 일반 회원인지 관리자인지 파악
     * 
     */
    @Override
    @Transactional(readOnly = true)
    public AuthorityType authorityCheck(Long teamId, String memberId)
    {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, memberId);

        if(teamMembers.size() == 0)
            return AuthorityType.NONE;
        return teamMembers.get(0).getAuthority();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamMemberDto> findTeamMember(Long teamId, String memberId)
    {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, memberId);
        List<TeamMemberDto> collect = teamMembers.stream().map(tm -> new TeamMemberDto(tm))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    @Transactional(readOnly = false)
    public void withdrawal(Long teamId, String memberId)
    {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, memberId);
        if(teamMembers.size() != 0) {
            teamRepository.deleteTeamMember(teamId, memberId);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public DisbandmentTeamResponse disbandmentTeam(Long teamId)
    {
        Team team = teamRepository.selectTeam(teamId);
        if (team == null)
            return null;
        teamRepository.deleteTeam(team);
        return new DisbandmentTeamResponse(teamId);
    }

    @Override
    @Transactional(readOnly = false)
    public UpdateAuthorityResponse updateAuthority(Long teamId, String memberId, AuthorityType authorityType)
    {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, memberId);
        if (teamMembers.size() == 0)
            return null;
        teamMembers.get(0).setAuthority(authorityType);
        return new UpdateAuthorityResponse(teamId, memberId, authorityType);
    }

}
