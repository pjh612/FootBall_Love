package com.deu.football_love.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MatchApplicationState;
import com.deu.football_love.domain.type.MatchState;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.StadiumFieldType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.match.AddMatchRequest;
import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.ApplyMatchRequest;
import com.deu.football_love.dto.match.ApproveMatchRequest;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.QueryMatchApplicationDto;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.dto.match.RefuseMatchApplicationRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;

import com.deu.football_love.service.CompanyService;
import com.deu.football_love.service.MatchService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.StadiumService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Slf4j
class MatchControllerTest {

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  MockMvc mvc;

  @Autowired
  private TeamService teamService;
  @Autowired
  private MatchService matchService;
  @Autowired
  private MemberService memberService;
  @Autowired
  private StadiumService stadiumService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  ObjectMapper mapper;

  private QueryMemberDto memberAInfo;
  private QueryMemberDto memberBInfo;
  private QueryMemberDto memberCInfo;
  private CreateTeamResponse teamAInfo;
  private CreateTeamResponse teamBInfo;
  AddCompanyResponse companyInfo;
  private AddStadiumResponse stadiumInfo;
  LoginInfo loginUserA;
  LoginInfo loginUserB;
  LoginInfo loginUserC;

  @BeforeEach
  public void init() {
    MemberJoinRequest joinA = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("?????????").pwd("1234").nickname("????????????").address(new Address("??????", "?????????", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    MemberJoinRequest joinB = MemberJoinRequest.memberJoinRequestBuilder().id("memberB")
        .name("?????????").pwd("1234").nickname("??????").address(new Address("??????", "?????????", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("jh@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
    MemberJoinRequest joinC = MemberJoinRequest.memberJoinRequestBuilder().id("memberC")
        .name("?????????").pwd("1234").nickname("??????").address(new Address("??????", "?????????", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("bk@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_BUSINESS).build();
    memberAInfo = memberService.join(joinA);
    memberBInfo = memberService.join(joinB);
    memberCInfo = memberService.join(joinC);
    companyInfo = companyService.addCompany("companyA", memberCInfo.getNumber(),
        new Address("city", "street", "zipcode"), "010-1234-1234", "companyA description");
    stadiumInfo = stadiumService.addStadium(companyInfo.getCompanyId(), StadiumFieldType.ARTIFICIAL_TURF, "30*50", 120000L);
    teamAInfo = teamService.createNewTeam(memberAInfo.getId(), "teamA", "??? A ??????");
    teamBInfo = teamService.createNewTeam(memberBInfo.getId(), "teamB", "??? B ??????");
    loginUserA = (LoginInfo) userDetailsService.loadUserByUsername("memberA");
    loginUserB = (LoginInfo) userDetailsService.loadUserByUsername("memberB");
    loginUserC = (LoginInfo) userDetailsService.loadUserByUsername("memberC");
    log.info("loginUserC Company = {}", loginUserC.getCompanyId());
  }

  @SneakyThrows
  @Test
  public void add() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchRequest addMatchRequest = new AddMatchRequest(stadiumInfo.getId(), reservationTime);

    mvc.perform(MockMvcRequestBuilders.post("/api/match").with(user(loginUserC))
        .contentType(MediaType.APPLICATION_JSON)
        //.param("stadiumId", Long.toString(addMatchRequest.getStadiumId()))
        // .param("reservationTime", "20181215T10:00:00"))
        .content(mapper.writeValueAsString(addMatchRequest)))
        .andExpect(status().isOk()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.post("/api/match").with(user(loginUserA))
        .contentType("application/json").content(mapper.writeValueAsString(addMatchRequest)))
        .andExpect(status().isForbidden()).andDo(print());
  }

  /**
   * userC ????????? ???????????? ????????? ????????? userC??? ????????? ?????? ??? - OK
   * userC ????????? ???????????? ????????? ????????? userA??? ????????? ?????? ??? - BadRequest
   */
  @SneakyThrows
  @Test
  public void cancel() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchRequest addMatchRequest = new AddMatchRequest(stadiumInfo.getId(), reservationTime);
    AddMatchResponse addMatchResponse = matchService
        .addMatch(addMatchRequest.getStadiumId(), loginUserC.getCompanyId(), addMatchRequest.getReservationTime());
    mvc.perform(MockMvcRequestBuilders.delete("/api/match/{matchId}",addMatchResponse.getMatchId()).with(user(loginUserC))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.delete("/api/match/{matchId}",addMatchResponse.getMatchId()).with(user(loginUserA))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andDo(print());
  }


  /**
   * 1. ???B??? ??????????????? ???A??? ????????? ????????? ??????????????? ?????? ??? -> Forbidden 2. ???A??? ??????????????? ???A??? ????????? ????????? ??????????????? ?????? ??? -> OK 3. ???A??? ????????? ????????? ???A??? ????????? ??????????????? ?????? ??? -> BadRequest
   */
  @SneakyThrows
  @Test
  public void apply() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchRequest addMatchRequest = new AddMatchRequest(stadiumInfo.getId(), reservationTime);

    AddMatchResponse addMatchResponse = matchService
        .addMatch(addMatchRequest.getStadiumId(), loginUserC.getCompanyId(), addMatchRequest.getReservationTime());
    ApplyMatchRequest applyMatchRequestA = new ApplyMatchRequest(teamAInfo.getTeamId(), addMatchResponse.getMatchId());
    ApplyMatchRequest applyMatchRequestB = new ApplyMatchRequest(teamBInfo.getTeamId(), addMatchResponse.getMatchId());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/apply").with(user(loginUserB))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(applyMatchRequestA)))
        .andExpect(status().isForbidden()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/apply").with(user(loginUserA))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(applyMatchRequestA)))
        .andExpect(status().isOk()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/apply").with(user(loginUserA))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(applyMatchRequestA)))
        .andExpect(status().isBadRequest()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/apply").with(user(loginUserB))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(applyMatchRequestB)))
        .andExpect(status().isOk()).andDo(print());
  }

  /**
   * ???B??? ???A?????? ?????? ????????? ?????? ????????? ?????? ??????(loginUserB)??? teamA ????????? ????????? ????????? ???????????? ???????????? ?????? ??? - Forbidden ????????? ?????? ??????(loginUserA)??? teamA ????????? ????????? ????????? ???????????? ???????????? ?????? ??? -
   * OK
   */
  @SneakyThrows
  @Test
  public void approve() {
    LocalDateTime reservationTime = LocalDateTime.now();
    AddMatchRequest addMatchRequest = new AddMatchRequest(stadiumInfo.getId(), reservationTime);

    AddMatchResponse match = matchService.addMatch(addMatchRequest.getStadiumId(), loginUserC.getCompanyId(), addMatchRequest.getReservationTime());
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    MatchApplicationResponse matchApplication = matchService.addMatchApplication(match.getMatchId(), teamBInfo.getTeamId());
    ApproveMatchRequest approveMatchRequest = new ApproveMatchRequest(matchApplication.getMatchApplicationId());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/approval/{matchId}", match.getMatchId()).with(user(loginUserB))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(approveMatchRequest)))
        .andExpect(status().isForbidden()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/approval/{matchId}", match.getMatchId()).with(user(loginUserA))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(approveMatchRequest)))
        .andExpect(status().isOk()).andDo(print());

    QueryMatchDto findMatch = matchService.findMatch(match.getMatchId());
    Assertions.assertEquals(MatchState.MATCHED, findMatch.getState());
    Assertions.assertEquals(teamAInfo.getTeamId(), findMatch.getTeamAId());
    Assertions.assertEquals(teamBInfo.getTeamId(), findMatch.getTeamBId());
  }

  @SneakyThrows
  @Test
  public void refuseMatchApplication() {
    LocalDateTime reservationTime = LocalDateTime.now();
    String refuseMessage = "?????? ????????? ?????????.";
    AddMatchRequest addMatchRequest = new AddMatchRequest(stadiumInfo.getId(), reservationTime);

    AddMatchResponse match = matchService.addMatch(addMatchRequest.getStadiumId(), loginUserC.getCompanyId(), addMatchRequest.getReservationTime());
    matchService.registerTeamA(match.getMatchId(), teamAInfo.getTeamId());
    MatchApplicationResponse matchApplication = matchService.addMatchApplication(match.getMatchId(), teamBInfo.getTeamId());
    RefuseMatchApplicationRequest refuseMatchApplicationRequest = new RefuseMatchApplicationRequest(matchApplication.getMatchApplicationId(), refuseMessage);
    mvc.perform(MockMvcRequestBuilders.post("/api/match/application/refuse").with(user(loginUserB))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(refuseMatchApplicationRequest)))
        .andExpect(status().isForbidden()).andDo(print());
    mvc.perform(MockMvcRequestBuilders.post("/api/match/application/refuse").with(user(loginUserA))
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(refuseMatchApplicationRequest)))
        .andExpect(status().isOk()).andDo(print());

    QueryMatchApplicationDto findMatchApplication = matchService.findMatchApplicationById(matchApplication.getMatchApplicationId());
    Assertions.assertEquals(refuseMessage, findMatchApplication.getRefuseMessage());
    Assertions.assertEquals(MatchApplicationState.REFUSED, findMatchApplication.getState());
  }
}