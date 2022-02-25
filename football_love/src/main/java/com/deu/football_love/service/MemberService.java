package com.deu.football_love.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.WithdrawalMember;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.auth.LoginResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import com.deu.football_love.repository.CompanyRepository;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.PostRepository;
import com.deu.football_love.repository.TeamMemberRepository;
import com.deu.football_love.repository.TeamRepository;
import com.deu.football_love.repository.WithdrawalMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberService {
  private final MemberRepository memberRepository;
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final PostRepository postRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final TeamService teamService;
  private final CompanyRepository companyRepository;
  private final WithdrawalMemberRepository withdrawalMemberRepository;

  @Transactional(readOnly = true)
  public LoginResponse login_jwt(LoginRequest loginRequest) {
    Member member = memberRepository.findById(loginRequest.getId())
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    if (member != null && member.getId().equals(loginRequest.getId())
        && passwordEncoder.matches(loginRequest.getPwd(), member.getPwd())) {
      List<String> roleList = Arrays.asList(member.getMemberType().name());
      String accessToken = jwtTokenProvider.createAccessToken(member.getId(), roleList);
      String refreshToken = jwtTokenProvider.createRefreshToken();
      return new LoginResponse("success", "create token success", accessToken, refreshToken,
          member.getNumber());
    } else
      return new LoginResponse("fail", "create token fail", null, null, null);
  }

  public QueryMemberDto join(MemberJoinRequest joinRequest) {
    String password = joinRequest.getPwd();
    String encodedPassword = passwordEncoder.encode(password);
    joinRequest.setPwd(encodedPassword);

    Member member = Member.memberBuilder().address(joinRequest.getAddress())
        .birth(joinRequest.getBirth()).email(joinRequest.getEmail()).id(joinRequest.getId())
        .pwd(joinRequest.getPwd()).nickname(joinRequest.getNickname()).name(joinRequest.getName())
        .phone(joinRequest.getPhone()).memberType(joinRequest.getType()).build();
    memberRepository.save(member);
    member.setCreatedBy(member.getNumber());
    member.setLastModifiedBy(member.getNumber());
    QueryMemberDto memberResponse = new QueryMemberDto(member);
    return memberResponse;
  }

  @Transactional(readOnly = true)
  public boolean isDuplicationId(String id) {
    return memberRepository.existsById(id);
  }

  public boolean isDuplicationEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  @Transactional(readOnly = true)
  public QueryMemberDto findMember(Long number) {
    Member member = memberRepository.findById(number)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    return new QueryMemberDto(member);
  }

  @Transactional(readOnly = true)
  public QueryMemberDto findMemberById(String id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    return new QueryMemberDto(member);
  }

  @Transactional(readOnly = true)
  public LoginInfo findMemberById_jwt(String id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    return new LoginInfo(member);
  }

  public QueryMemberDto findQueryMemberDtoByNumber(Long number) {
    QueryMemberDto findMember = memberRepository.findQueryMemberDtoByNumber(number)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    return findMember;
  }

  public QueryMemberDto modifyByMemberNumber(Long memberNum, UpdateMemberRequest request) {
    Member findMember = memberRepository.findById(memberNum)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    if (!passwordEncoder.matches(request.getPwd(), findMember.getPwd()))
      findMember.setPwd(passwordEncoder.encode(request.getPwd()));
    findMember.setEmail(request.getEmail());
    findMember.setNickname(request.getNickname());
    findMember.setAddress(request.getAddress());
    findMember.setPhone(request.getPhone());
    return new QueryMemberDto(findMember);
  }

  public QueryMemberDto modifyByMemberId(String memberId, UpdateMemberRequest request) {
    Member findMember = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    if (!passwordEncoder.matches(request.getPwd(), findMember.getPwd()))
      findMember.setPwd(passwordEncoder.encode(request.getPwd()));
    findMember.setEmail(request.getEmail());
    findMember.setNickname(request.getNickname());
    findMember.setAddress(request.getAddress());
    findMember.setPhone(request.getPhone());
    return new QueryMemberDto(findMember);
  }

  public boolean withdraw(String id) {
    Member findMember = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    if (withdrawalMemberRepository.existsByMemberId(id))
      return false;
    withdrawalMemberRepository.save(new WithdrawalMember(findMember));
    while (findMember.getPosts().size() != 0) {
      Post post = findMember.getPosts().get(0);
      post.deletePost();
      postRepository.delete(post);
    }
    /**
     * 멤버 소유의 팀 삭제, 멤버 소속 탈퇴
     */
    List<TeamMember> teamMembers = findMember.getTeamMembers();
    while (teamMembers.size() != 0) {
      TeamMember curTeamMember = teamMembers.get(0);
      if (curTeamMember.getType() == TeamMemberType.LEADER) {
        teamService.disbandmentTeam(curTeamMember.getTeam().getId());
      } else {
        curTeamMember.deleteTeamMember();
        teamMemberRepository.deleteByTeamIdAndMemberNumber(curTeamMember.getTeam().getId(),
            curTeamMember.getMember().getNumber());
      }
    }
    // 사업자일 경우 컴퍼니 삭제
    if (findMember.getMemberType() == MemberType.ROLE_BUSINESS) {
      companyRepository.delete(findMember.getCompany());
      findMember.getCompany().deleteCompany();
    }
    return true;
  }

  /*
   * @Transactional(readOnly = true) public TeamMemberType checkMemberAuthority(String memberId,
   * String teamName) { return memberRepository.selectMemberAuthority(memberId, teamName); }
   */
}
