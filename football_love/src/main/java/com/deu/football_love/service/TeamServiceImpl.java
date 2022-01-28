package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.team.*;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public QueryTeamDto getTeamInfo(Long teamId) {
        Team team = teamRepository.selectTeam(teamId);
        if (team == null)
            return null;
        return QueryTeamDto.from(team);
    }

    @Override
    public CreateTeamResponse createNewTeam(String creator, String teamName) {
        Team team = new Team();
        Member findMember = memberRepository.selectMemberById(creator);
        if (findMember == null)
            return null;
        TeamMember teamMember = new TeamMember(team, findMember, TeamMemberType.LEADER);
        team.setName(teamName);
        teamMember.setTeam(team);
        teamMember.setMember(findMember);
        team.getTeamMembers().add(teamMember);
        findMember.getTeamMembers().add(teamMember);
        teamRepository.insertTeam(team);
        teamRepository.insertNewTeamMember(teamMember);
        return new CreateTeamResponse(team.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public QueryTeamDto findTeam(Long teamId) {
        Team team = teamRepository.selectTeam(teamId);
        if (team == null)
            return null;
        return QueryTeamDto.from(team);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryTeamDto findTeamByName(String teamName) {
        Team team = teamRepository.selectTeamByName(teamName);
        if (team == null)
            return null;
        return QueryTeamDto.from(team);
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
        Member findMember = memberRepository.selectMemberById(memberId);

        ApplicationJoinTeam application = new ApplicationJoinTeam(findTeam, findMember, message);
        findTeam.getApplicationJoinTeams().add(application);
        findMember.getApplicationJoinTeams().add(application);

        teamRepository.insertNewApplication(application);
    }

    @Override
    public AcceptApplicationResponse acceptApplication(Long teamId, String memberId) {
        ApplicationJoinTeam findApplication = teamRepository.selectApplication(teamId, memberId);
        Team findTeam = teamRepository.selectTeam(teamId);
        Member findMember = memberRepository.selectMemberById(memberId);
        if (findApplication == null || findTeam == null || findMember == null)
            return null;

        findApplication.delete();
        teamRepository.deleteApplication(findApplication);

        TeamMember newTeamMember = new TeamMember(findTeam, findMember, TeamMemberType.COMMON);
        findTeam.getTeamMembers().add(newTeamMember);
        findMember.getTeamMembers().add(newTeamMember);
        teamRepository.insertNewTeamMember(newTeamMember);

        return new AcceptApplicationResponse(teamId, memberId);
    }

    /**
     * 팀에 속해 있는지, 속해 있다면 일반 회원인지 관리자인지 파악
     */
    @Override
    @Transactional(readOnly = true)
    public TeamMemberType authorityCheck(Long teamId, Long memberNumber) {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, memberNumber);

        if (teamMembers.size() == 0)
            return TeamMemberType.NONE;
        return teamMembers.get(0).getType();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QueryTeamMemberDto> findTeamMember(Long teamId, Long memberNumber) {
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, memberNumber);
        List<QueryTeamMemberDto> collect = teamMembers.stream().map(tm -> new QueryTeamMemberDto(tm))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QueryTeamMemberDto> findTeamMemberByMemberId(Long teamId, String memberId) {
        Member findMember = memberRepository.selectMemberById(memberId);
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, findMember.getNumber());
        List<QueryTeamMemberDto> collect = teamMembers.stream().map(tm -> new QueryTeamMemberDto(tm))
                .collect(Collectors.toList());
        return collect;

    }


    @Override
    public void withdrawal(Long teamId, String memberId) {
        Member findMember = memberRepository.selectMemberById(memberId);
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, findMember.getNumber());
        if (teamMembers.size() != 0) {
            teamRepository.deleteTeamMember(teamId, findMember.getNumber());
        }
    }

    @Override
    public DisbandmentTeamResponse disbandmentTeam(Long teamId) {
        Team team = teamRepository.selectTeam(teamId);
        if (team == null)
            return null;
        while (team.getBoards().size() != 0) {
            Board board = team.getBoards().get(0);
            while (board.getPosts().size() != 0) {
                Post post = board.getPosts().get(0);
                post.deletePost();
                postRepository.deletePost(post);
            }
            board.getTeam().getBoards().remove(board);
            board.setTeam(null);
            boardRepository.deleteBoard(board);
        }
        while (team.getTeamMembers().size() != 0) {
            TeamMember teamMember = team.getTeamMembers().get(0);
            Long teamMemberId = teamMember.getId();
            teamMember.deleteTeamMember();;
            teamRepository.deleteTeamMember(team.getId(), teamMemberId);
        }
        teamRepository.deleteTeam(team);
        return new DisbandmentTeamResponse(teamId);
    }

    @Override
    public UpdateAuthorityResponse updateAuthority(Long teamId, String memberId, TeamMemberType authorityType) {
        Member findMember = memberRepository.selectMemberById(memberId);
        List<TeamMember> teamMembers = teamRepository.selectTeamMember(teamId, findMember.getNumber());
        if (teamMembers.size() == 0)
            return null;
        teamMembers.get(0).setType(authorityType);
        return new UpdateAuthorityResponse(teamId, memberId, authorityType);
    }

}
