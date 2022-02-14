package com.deu.football_love.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deu.football_love.repository.StadiumRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;

@SpringBootTest
@Transactional
@Slf4j
public class MatchServiceTest {

  @Autowired
  private MatchService matchService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private StadiumRepository stadiumRepository;

  @Autowired
  private StadiumService stadiumService;

  @Autowired
  private CompanyService companyService;

  @Test
  public void 매치_생성_찾기() {
    MemberJoinRequest joinInfo = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    QueryMemberDto memberInfo = memberService.join(joinInfo);
    CreateTeamResponse teamInfo = teamService
        .createNewTeam(memberInfo.getId(), "FC FLOW", "팀 FC FLOW 소개");

    Address address = new Address("1", "1", "9");
    AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo.getNumber(),
        address, "010-6779-3476", "좋은 회사");

    AddStadiumResponse stadiumInfo =
        stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
    AddMatchResponse matchDto =
        matchService.addMatch(teamInfo.getTeamId(), stadiumInfo.getId(), LocalDateTime.now());
    QueryMatchDto findMatchDto = matchService.findMatch(matchDto.getMatchId());

    assertAll(() -> assertEquals(matchDto.getMatchId(), findMatchDto.getMatchId()),
        () -> assertTrue(matchDto.getTeamName().equals(findMatchDto.getTeamName())),
        () -> assertEquals(matchDto.getStadiumId(), findMatchDto.getStadiumId()),
        () -> assertEquals(matchDto.getReservation_time(), findMatchDto.getReservation_time()));
  }

  @Test
  public void 매치_신청_승인() {
    MemberJoinRequest joinInfo1 = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    QueryMemberDto memberInfo1 = memberService.join(joinInfo1);
    CreateTeamResponse teamInfo1 = teamService
        .createNewTeam(memberInfo1.getId(), "FC FLOW", "팀 FC FLOW 소개");

    MemberJoinRequest joinInfo2 = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns2")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp2@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto memberInfo2 = memberService.join(joinInfo2);
    CreateTeamResponse teamInfo2 = teamService
        .createNewTeam(memberInfo2.getId(), "FC LOL", "팀 FC FLOW 소개");

    Address address = new Address("1", "1", "9");
    AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo1.getNumber(),
        address, "010-6779-3476", "좋은 회사");

    AddStadiumResponse stadiumInfo =
        stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
    AddMatchResponse matchDto =
        matchService.addMatch(teamInfo1.getTeamId(), stadiumInfo.getId(), LocalDateTime.now());
    MatchApplicationResponse matchApplication =
        matchService.addMatchApplication(teamInfo2.getTeamId(), matchDto.getMatchId());

    MatchApproveResponse match =
        matchService.approveMatch(matchDto.getMatchId(), matchApplication.getMatchApplicationId());

    assertTrue(match.getApproval());

  }

  @Test
  public void 매치_취소() {
    MemberJoinRequest joinInfo = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    QueryMemberDto memberInfo = memberService.join(joinInfo);
    CreateTeamResponse teamInfo = teamService
        .createNewTeam(memberInfo.getId(), "FC FLOW", "팀 FC FLOW 소개");

    Address address = new Address("1", "1", "9");
    AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo.getNumber(),
        address, "010-6779-3476", "좋은 회사");

    AddStadiumResponse stadiumInfo =
        stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
    AddMatchResponse matchDto =
        matchService.addMatch(teamInfo.getTeamId(), stadiumInfo.getId(), LocalDateTime.now());
    matchService.cancelMatch(matchDto.getMatchId());
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> matchService.findMatch(matchDto.getMatchId()));
  }

  @Test
  public void 매치_수정() {
    MemberJoinRequest joinInfo = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns1")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    QueryMemberDto memberInfo = memberService.join(joinInfo);
    CreateTeamResponse teamInfo = teamService
        .createNewTeam(memberInfo.getId(), "FC FLOW", "팀 FC FLOW 소개");

    Address address = new Address("1", "1", "9");
    AddCompanyResponse companyInfo = companyService.addCompany("(주)아프리카TV", memberInfo.getNumber(),
        address, "010-6779-3476", "좋은 회사");

    AddStadiumResponse stadiumInfo =
        stadiumService.addStadium(companyInfo.getCompanyId(), "풋살장", "30*50", 120000L);
    AddStadiumResponse soccerStadiumInfo =
        stadiumService.addStadium(companyInfo.getCompanyId(), "축구장", "120*60", 180000L);

    AddMatchResponse matchDto =
        matchService.addMatch(teamInfo.getTeamId(), stadiumInfo.getId(), LocalDateTime.now());
    ModifyMatchResponse modifyMatchDto = matchService.modifyMatch(matchDto.getMatchId(),
        soccerStadiumInfo.getId(), LocalDateTime.now().minusHours(2L));
    QueryMatchDto findMatchDto = matchService.findMatch(modifyMatchDto.getMatchId());

    assertAll(() -> assertEquals(matchDto.getMatchId(), findMatchDto.getMatchId()),
        () -> assertTrue(matchDto.getTeamName().equals(findMatchDto.getTeamName())),
        () -> assertNotEquals(matchDto.getStadiumId(), findMatchDto.getStadiumId()),
        () -> assertNotEquals(matchDto.getReservation_time(), findMatchDto.getReservation_time()));
  }

  @Test
  public void 매치_리스트_조회() {
    MemberJoinRequest joinRequestA = MemberJoinRequest.memberJoinRequestBuilder().id("company")
        .name("박진형").pwd("1234").nickname("사업자A").address(new Address("부산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("business_a@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    MemberJoinRequest joinRequestB = MemberJoinRequest.memberJoinRequestBuilder().id("ROLE_NORMAL")
        .name("유시준").pwd("1234").nickname("일반회원A").address(new Address("부산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("normal_a@naver.com").phone("010-1111-2223")
        .type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto businessMember = memberService.join(joinRequestA);
    QueryMemberDto normalMember = memberService.join(joinRequestB);
    CreateTeamResponse teamA = teamService.createNewTeam(normalMember.getId(), "teamA", "팀 A 소개");
    AddCompanyResponse companyA = companyService
        .addCompany("companyA", businessMember.getNumber(), new Address("부산", "행복길", "11"),
            "010-1111-2222", "A급 경기장을 보유한 companyA입니다.");
    AddStadiumResponse stadiumA = stadiumService
        .addStadium(companyA.getCompanyId(), "천연잔디", "5x5", 60000L);
    AddStadiumResponse stadiumB = stadiumService
        .addStadium(companyA.getCompanyId(), "인조잔디", "5x5", 50000L);
    matchService.addMatch(teamA.getTeamId(), stadiumA.getId(), LocalDateTime.now());
    matchService.addMatch(teamA.getTeamId(), stadiumB.getId(), LocalDateTime.now());

    List<QueryMatchDto> matchList = matchService.findAllByCompanyId(companyA.getCompanyId());
    Assertions.assertEquals(2, matchList.size());
  }
}
