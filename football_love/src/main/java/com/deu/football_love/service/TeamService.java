package com.deu.football_love.service;

import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.exception.NotExistDataException;
import com.deu.football_love.exception.NotTeamMemberException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.deu.football_love.domain.ApplicationJoinTeam;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamBoard;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.dto.team.AcceptApplicationResponse;
import com.deu.football_love.dto.team.ApplicationJoinTeamDto;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.DisbandmentTeamResponse;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.dto.team.QueryTeamListItemDto;
import com.deu.football_love.dto.team.QueryTeamMemberDto;
import com.deu.football_love.dto.team.UpdateAuthorityResponse;
import com.deu.football_love.repository.ApplicationJoinTeamRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamBoardRepository;
import com.deu.football_love.repository.TeamMemberRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TeamService {

  private final TeamRepository teamRepository;
  private final MemberRepository memberRepository;
  private final PostRepository postRepository;
  private final TeamBoardRepository boardRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final ApplicationJoinTeamRepository applicationJoinTeamRepository;
  private final GcpStorageService gcpStorageService;

  @Transactional(readOnly = true)
  public QueryTeamDto getTeamInfo(Long teamId) {
    Team findTeam = teamRepository.findByIdWithTeamMember(teamId).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    return QueryTeamDto.from(findTeam);
  }

  public CreateTeamResponse createNewTeam(String creator, String teamName, String introduce) {
    Team team = new Team();
    Member findMember = memberRepository.findById(creator).orElseThrow(() -> new IllegalArgumentException("no such member data."));
    TeamMember teamMember = new TeamMember(team, findMember, TeamMemberType.LEADER);
    team.setName(teamName);
    team.setIntroduce(introduce);
    teamMember.setTeam(team);
    teamMember.setMember(findMember);
    team.getTeamMembers().add(teamMember);
    findMember.getTeamMembers().add(teamMember);
    teamRepository.save(team);
    teamMemberRepository.save(teamMember);
    return new CreateTeamResponse(team.getId(), team.getName());
  }

  @Transactional(readOnly = true)
  public QueryTeamDto findTeam(Long teamId) {
    Team findTeam = teamRepository.findByIdWithTeamMember(teamId).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    return QueryTeamDto.from(findTeam);
  }

  @Transactional(readOnly = true)
  public QueryTeamDto findTeamByName(String teamName) {
    Team team = teamRepository.findByNameWithTeamMember(teamName).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    return QueryTeamDto.from(team);
  }

  @Transactional(readOnly = true)
  public ApplicationJoinTeamDto findApplication(Long teamId, String memberId) {
    ApplicationJoinTeam application =
        applicationJoinTeamRepository.findByTeamIdAndMemberId(teamId, memberId).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    return new ApplicationJoinTeamDto(application);
  }

  public void applyToTeam(Long teamId, String memberId, String message) {

    Team findTeam = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("no such member data."));

    ApplicationJoinTeam application = new ApplicationJoinTeam(findTeam, findMember, message);
    findTeam.getApplicationJoinTeams().add(application);
    findMember.getApplicationJoinTeams().add(application);

    applicationJoinTeamRepository.save(application);
  }

  public AcceptApplicationResponse acceptApplication(Long teamId, String memberId) {
    ApplicationJoinTeam findApplication =
        applicationJoinTeamRepository.findByTeamIdAndMemberId(teamId, memberId).orElseThrow(() -> new IllegalArgumentException("no such Team data"));;
    Team findTeam = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("no such member data."));;
    findApplication.delete();
    applicationJoinTeamRepository.delete(findApplication);
    TeamMember newTeamMember = new TeamMember(findTeam, findMember, TeamMemberType.COMMON);
    findTeam.getTeamMembers().add(newTeamMember);
    findMember.getTeamMembers().add(newTeamMember);
    teamMemberRepository.save(newTeamMember);

    return new AcceptApplicationResponse(teamId, memberId);
  }

  /**
   * 팀에 속해 있는지, 속해 있다면 일반 회원인지 관리자인지 파악
   */
  @Transactional(readOnly = true)
  public TeamMemberType authorityCheck(Long teamId, Long memberNumber) {
    Optional<TeamMember> findTeamMember = teamMemberRepository.findByTeamIdAndMemberNumber(teamId, memberNumber);
    if (!findTeamMember.isPresent()) {
      return TeamMemberType.NONE;
    }
    return findTeamMember.get().getType();
  }

  @Transactional(readOnly = true)
  public TeamMemberType authorityCheck(String teamName, String memberId) {
    Optional<TeamMember> findTeamMember = teamMemberRepository.findByTeamNameAndMemberId(teamName, memberId);
    if (!findTeamMember.isPresent()) {
      return TeamMemberType.NONE;
    }
    return findTeamMember.get().getType();
  }

  @Transactional(readOnly = true)
  public QueryTeamMemberDto findTeamMemberByTeamIdAndMemberNumber(Long teamId, Long memberNumber) {
    TeamMember teamMember = teamMemberRepository.findByTeamIdAndMemberNumber(teamId, memberNumber)
        .orElseThrow(() -> new NotTeamMemberException("no such team_member data"));
    return new QueryTeamMemberDto(teamMember);
  }

  @Transactional(readOnly = true)
  public QueryTeamMemberDto findTeamMemberByTeamIdAndMemberId(Long teamId, String memberId) {
    TeamMember teamMember =
        teamMemberRepository.findByTeamIdAndMemberId(teamId, memberId).orElseThrow(() -> new NotTeamMemberException("no such team_member data"));
    return new QueryTeamMemberDto(teamMember);
  }

  @Transactional(readOnly = true)
  public List<QueryTeamMemberDto> findTeamMembersByTeamId(Long teamId) {
    return teamMemberRepository.findTeamMembersByTeamId(teamId).stream().map(teamMember -> new QueryTeamMemberDto(teamMember))
        .collect(Collectors.toList());
  }

  public void withdrawal(Long teamId, String memberId) {
    Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new NotExistDataException("no such member data."));
    if (!teamMemberRepository.existsByTeamIdAndMemberId(teamId, memberId)) {
      throw new NotTeamMemberException("no such team_member data");
    }
    teamMemberRepository.deleteByTeamIdAndMemberNumber(teamId, findMember.getNumber());
  }

  public DisbandmentTeamResponse disbandmentTeam(Long teamId) {
    Team findTeam = teamRepository.findById(teamId).orElseThrow(() -> new NotExistDataException("no such Team data"));
    while (findTeam.getBoards().size() != 0) {
      TeamBoard board = findTeam.getBoards().get(0);
      while (board.getPosts().size() != 0) {
        Post post = board.getPosts().get(0);
        post.deletePost();
        postRepository.delete(post);
      }
      board.getTeam().getBoards().remove(board);
      board.setTeam(null);
      boardRepository.delete(board);
    }
    while (findTeam.getTeamMembers().size() != 0) {
      TeamMember teamMember = findTeam.getTeamMembers().get(0);
      Long teamMemberId = teamMember.getId();
      teamMember.deleteTeamMember();;
      teamMemberRepository.deleteByTeamIdAndMemberNumber(teamId, teamMemberId);
    }
    teamRepository.delete(findTeam);
    return new DisbandmentTeamResponse(teamId);
  }


  public UpdateAuthorityResponse updateAuthority(Long teamId, String memberId, TeamMemberType authorityType) {
    Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new NotExistDataException("no such member data."));
    TeamMember teamMember = teamMemberRepository
        .findByTeamIdAndMemberNumber(teamId, findMember.getNumber())
        .orElseThrow(() -> new NotExistDataException("no such team_member data"));
    teamMember.setType(authorityType);
    return new UpdateAuthorityResponse(teamId, memberId, authorityType);
  }

  public List<QueryTeamListItemDto> findAllTeamByMemberNumber(Long memberNumber) {
    List<QueryTeamListItemDto> findTeams = teamMemberRepository.findAllTeamByMemberNumber(memberNumber);
    return findTeams;
  }

  @SneakyThrows
  public String updateTeamProfile(Long teamId, MultipartFile profileImg, String introduce) {
    Team findTeam = teamRepository.findById(teamId).orElseThrow(() -> new NotExistDataException("no such team data."));
    String profileImgUri = findTeam.getProfileImgUri();
    if (profileImg != null) {
      profileImgUri = gcpStorageService.updateTeamProfileImg(profileImg, teamId);
    }
    findTeam.updateTeamProfile(profileImgUri, introduce);
    return profileImgUri;
  }

}
