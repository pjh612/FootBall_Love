package com.deu.football_love.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.company.AddCompanyRequest;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.service.TeamBoardService;
import com.deu.football_love.service.CommentService;
import com.deu.football_love.service.CompanyService;
import com.deu.football_love.service.MatchService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.PostService;
import com.deu.football_love.service.StadiumService;
import com.deu.football_love.service.TeamService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CompanyControllerTest {

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  MockMvc mvc;

  @Autowired
  private MemberService memberService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private MatchService matchService;

  @Autowired
  private CompanyService companyService;
  @Autowired
  private StadiumService stadiumService;

  @Autowired
  private CommentService commentService;

  @SneakyThrows
  @Test
  /**
   * business 유저만 해당 api 사용 가능
   */
  void getMatchListRoleTest() {
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
    matchService.addMatch( stadiumA.getId(),companyA.getCompanyId(), LocalDateTime.now());
    matchService.addMatch(stadiumB.getId(),companyA.getCompanyId(), LocalDateTime.now());
    UserDetails userDetails = userDetailsService.loadUserByUsername(businessMember.getId());
    for (Object o : userDetails.getAuthorities().stream().toArray()) {
      System.out.println("hello" +o);
    }

    mvc.perform(MockMvcRequestBuilders.get("/api/company/match").contentType(MediaType.APPLICATION_JSON)
        .with(user(userDetailsService.loadUserByUsername(normalMember.getId()))))
        .andExpect(status().isForbidden());
    mvc.perform(MockMvcRequestBuilders.get("/api/company/match").contentType(MediaType.APPLICATION_JSON)
        .with(user(userDetailsService.loadUserByUsername(businessMember.getId()))))
        .andExpect(status().isOk())
        .andDo(print());
  }
}