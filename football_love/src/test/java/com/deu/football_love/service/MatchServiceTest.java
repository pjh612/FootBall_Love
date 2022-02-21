
package com.deu.football_love.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.type.MatchApplicationState;
import com.deu.football_love.domain.type.MatchState;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.repository.MatchApplicationRepository;
import com.deu.football_love.repository.MatchRepository;
import com.deu.football_love.repository.StadiumRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
public class MatchServiceTest {

  @Autowired
  private MatchService matchService;
  @Autowired
  private MatchRepository matchRepository;
  @Autowired
  private MatchApplicationRepository matchApplicationRepository;
  @Autowired
  private StadiumRepository stadiumRepository;
  @Autowired
  private TeamService teamService;
  @Autowired
  private MemberService memberService;
  @Autowired
  private StadiumService stadiumService;
  @Autowired
  private CompanyService companyService;

  private QueryMemberDto memberAInfo;
  private QueryMemberDto memberBInfo;
  private QueryMemberDto memberCInfo;
  private CreateTeamResponse teamAInfo;
  private CreateTeamResponse teamBInfo;
  AddCompanyResponse companyInfo;
  private AddStadiumResponse stadiumInfo;

  @BeforeEach
  public void init() {
    MemberJoinRequest joinA = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    MemberJoinRequest joinB = MemberJoinRequest.memberJoinRequestBuilder().id("memberB")
        .name("박진형").pwd("1234").nickname("진형").address(new Address("부산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("jh@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    MemberJoinRequest joinC = MemberJoinRequest.memberJoinRequestBuilder().id("memberC")
        .name("유병각").pwd("1234").nickname("병각").address(new Address("서울", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("bk@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    memberAInfo = memberService.join(joinA);
    memberBInfo = memberService.join(joinB);
    memberCInfo = memberService.join(joinC);
    companyInfo = companyService.addCompany("companyA", memberCInfo.getNumber(),
        new Address("city", "street", "zipcode"), "010-1234-1234", "companyA description");
    stadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
    teamAInfo = teamService.createNewTeam(memberAInfo.getId(), "teamA", "팀 A 소개");
    teamBInfo = teamService.createNewTeam(memberBInfo.getId(), "teamB", "팀 B 소개");
  }

  @Test
  public void 매치_생성() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse response = matchService
        .addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime);

    QueryMatchDto findMatch = matchService.findMatch(response.getMatchId());
    Assertions.assertEquals(reservationTime, findMatch.getReservationTime());
    Assertions.assertEquals(response.getMatchId(), findMatch.getMatchId());
    Assertions.assertEquals(MatchState.EMPTY, findMatch.getState());
    Assertions.assertEquals(stadiumInfo.getId(), findMatch.getStadiumId());
    Assertions.assertNull(findMatch.getTeamAId());
    Assertions.assertNull(findMatch.getTeamBId());
    Assertions.assertNull(findMatch.getTeamAName());
    Assertions.assertNull(findMatch.getTeamBName());
  }

  /**
   * 빈 매치에 팀 A가 등록을 함
   */
  @Test
  public void 매치_등록() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse match = matchService
        .addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime);
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    QueryMatchDto findMatch = matchService.findMatch(match.getMatchId());

    assertEquals(MatchState.WAITING, findMatch.getState());
    assertEquals(teamAInfo.getTeamId(), findMatch.getTeamAId());
    assertEquals(teamAInfo.getTeamName(), findMatch.getTeamAName());

  }

  /**
   * 팀A가 등록되어 대기중인 매치에 팀 B가 등록을 함
   */
  @Test
  public void 매치_신청() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse match = matchService
        .addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime);
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    MatchApplicationResponse matchApplicationResponse = matchService
        .addMatchApplication(match.getMatchId(), teamBInfo.getTeamId());

    Optional<Matches> findMatch = matchRepository.findById(match.getMatchId());
    Optional<MatchApplication> findMatchApplication = matchApplicationRepository
        .findByIdWithJoin(matchApplicationResponse.getMatchApplicationId());
    Assertions.assertTrue(findMatch.isPresent());
    Assertions.assertTrue(findMatchApplication.isPresent());
    assertEquals(MatchState.WAITING, findMatch.get().getState());
    assertEquals(MatchApplicationState.WAITING, findMatchApplication.get().getState());
  }

  /**
   * 팀A가 팀B의 매치 신청을 수락하고 매치가 성사됨
   */
  @Test
  public void 매치_성사() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse match = matchService
        .addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime);
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    MatchApplicationResponse matchApplicationResponse = matchService
        .addMatchApplication(match.getMatchId(), teamBInfo.getTeamId());
    MatchApproveResponse matchApproveResponse = matchService
        .approveMatchApplication(match.getMatchId(), matchApplicationResponse.getMatchApplicationId(), memberAInfo.getNumber());
    Optional<Matches> findMatch = matchRepository.findById(match.getMatchId());
    Optional<MatchApplication> findMatchApplication = matchApplicationRepository
        .findByIdWithJoin(matchApplicationResponse.getMatchId());

    Assertions.assertTrue(findMatch.isPresent());
    Assertions.assertFalse(findMatchApplication.isPresent());
    assertEquals(MatchState.MATCHED, findMatch.get().getState());
    assertEquals(teamAInfo.getTeamId(), findMatch.get().getTeamA().getId());
    assertEquals(teamBInfo.getTeamId(), findMatch.get().getTeamB().getId());
    assertEquals(reservationTime, findMatch.get().getReservationTime());
  }


  @Test
  public void 매치_취소() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse match = matchService
        .addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime);
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    matchService.cancelMatch(match.getMatchId(), companyInfo.getCompanyId());
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> matchService.findMatch(match.getMatchId()));
  }

  @Test
  public void 매치_거절() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse match = matchService
        .addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime);
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    MatchApplicationResponse matchApplicationResponse = matchService.addMatchApplication(match.getMatchId(), teamBInfo.getTeamId());
    String refuseMessage = "거절 메시지 입니다.";
    matchService.refuseMatchApplication(matchApplicationResponse.getMatchApplicationId(), refuseMessage, memberAInfo.getNumber());

    Optional<Matches> findMatch = matchRepository.findById(match.getMatchId());
    Optional<MatchApplication> findMatchApplication = matchApplicationRepository
        .findByIdWithJoin(matchApplicationResponse.getMatchApplicationId());

    Assertions.assertTrue(findMatch.isPresent());
    Assertions.assertTrue(findMatchApplication.isPresent());
    assertEquals(MatchApplicationState.REFUSED, findMatchApplication.get().getState());
    assertEquals(teamAInfo.getTeamId(), findMatch.get().getTeamA().getId());
    assertEquals(reservationTime, findMatch.get().getReservationTime());
  }

  /**
   * 1,2 Waiting, 3 Matched, 4,5,6 Empty
   */
  @Test
  public void 타입별_매치_조회() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchResponse match1 = matchService.addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime.plusHours(1));
    AddMatchResponse match2 = matchService.addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime.plusHours(2));
    AddMatchResponse match3 = matchService.addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime.plusHours(3));

    AddMatchResponse match4 = matchService.addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime.plusHours(4));
    AddMatchResponse match5 = matchService.addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime.plusHours(4));
    AddMatchResponse match6 = matchService.addMatch(stadiumInfo.getId(), companyInfo.getCompanyId(), reservationTime.plusHours(4));

    matchService.registerTeamA(match1.getMatchId(), teamAInfo.getTeamId());
    matchService.registerTeamA(match2.getMatchId(), teamAInfo.getTeamId());
    matchService.registerTeamA(match3.getMatchId(), teamAInfo.getTeamId());
    MatchApplicationResponse matchApplicationResponse = matchService.addMatchApplication(match3.getMatchId(), teamBInfo.getTeamId());
    matchService.approveMatchApplication(match3.getMatchId(), matchApplicationResponse.getMatchApplicationId(), memberAInfo.getNumber());

    Page<QueryMatchDto> emptyResult = matchService.findAllMatchByState(MatchState.EMPTY, null);
    Page<QueryMatchDto> waitingResult = matchService.findAllMatchByState(MatchState.WAITING, null);
    Page<QueryMatchDto> matchedResult = matchService.findAllMatchByState(MatchState.MATCHED, null);
    Assertions.assertEquals(3, emptyResult.getTotalElements());
    Assertions.assertEquals(2, waitingResult.getTotalElements());
    Assertions.assertEquals(1, matchedResult.getTotalElements());
  }

  @Test
  public void 회사별_매치_조회() {
    matchService.findAllByCompanyId(companyInfo.getCompanyId());
  }

}

