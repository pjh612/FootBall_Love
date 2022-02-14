package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.team.CreateTeamRequest;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.JoinApplyRequest;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class TeamControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  TeamService teamService;

  @Autowired
  MemberService memberService;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  private ObjectMapper mapper;

  private static final String BASE_URL = "/api/team";

  private final LocalDate BIRTH_DAY = LocalDate.of(2000, 1, 1);

  private final Address ADDRESS = new Address("양산", "행복길", "11");

  private QueryMemberDto member;

  private UserDetails userDetails;

  @BeforeEach
  public void init() {
    // 회원가입
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.NORMAL).build();
    member = memberService.join(joinRequest);
    userDetails = userDetailsService.loadUserByUsername(member.getId());
  }

  @Test
  public void 팀생성_테스트() throws Exception {

    CreateTeamRequest createTeamRequest =
        new CreateTeamRequest("FC진형", "부산시 가야동의 축구장을 거점으로 하는 국내 최대의 풋살 클럽 FC진형입니다.");


    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).with(user(userDetails))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createTeamRequest)).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void 팀가입신청_테스트() throws JsonProcessingException, Exception {
    CreateTeamRequest createTeamRequest =
        new CreateTeamRequest("FC진형", "부산시 가야동의 축구장을 거점으로 하는 국내 최대의 풋살 클럽 FC진형입니다.");
    CreateTeamResponse team = teamService.createNewTeam(member.getId(),
        createTeamRequest.getTeamName(), createTeamRequest.getTeamIntroduce());

    JoinApplyRequest joinApplyRequest = new JoinApplyRequest("청소년 국대출신");
    mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/join_requestion/" + team.getTeamId())
        .with(user(userDetails)).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(joinApplyRequest)).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void 팀가입수락_테스트() throws JsonProcessingException, Exception {
    CreateTeamRequest createTeamRequest =
        new CreateTeamRequest("FC진형", "부산시 가야동의 축구장을 거점으로 하는 국내 최대의 풋살 클럽 FC진형입니다.");
    CreateTeamResponse team = teamService.createNewTeam(member.getId(),
        createTeamRequest.getTeamName(), createTeamRequest.getTeamIntroduce());

    MemberJoinRequest newMemberJoinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dddd")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("dddd@naver.com").phone("010-1111-2222").type(MemberType.NORMAL).build();
    QueryMemberDto newMember = memberService.join(newMemberJoinRequest);

    teamService.applyToTeam(team.getTeamId(), newMember.getId(), "화이팅!!");

    mvc.perform(MockMvcRequestBuilders
        .post(BASE_URL + "/join_acception/" + team.getTeamId() + "/" + newMember.getId())
        .with(user(userDetails)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void 팀멤버탈퇴_테스트() throws JsonProcessingException, Exception {
    CreateTeamRequest createTeamRequest =
        new CreateTeamRequest("FC진형", "부산시 가야동의 축구장을 거점으로 하는 국내 최대의 풋살 클럽 FC진형입니다.");
    CreateTeamResponse team = teamService.createNewTeam(member.getId(),
        createTeamRequest.getTeamName(), createTeamRequest.getTeamIntroduce());

    MemberJoinRequest newMemberJoinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dddd")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("dddd@naver.com").phone("010-1111-2222").type(MemberType.NORMAL).build();
    QueryMemberDto newMember = memberService.join(newMemberJoinRequest);

    teamService.applyToTeam(team.getTeamId(), newMember.getId(), "화이팅!!");
    teamService.acceptApplication(team.getTeamId(), newMember.getId());

    UserDetails newUserDetails = userDetailsService.loadUserByUsername(newMember.getId());
    mvc.perform(MockMvcRequestBuilders
        .delete(BASE_URL + "/" + team.getTeamId() + "/member/" + newMember.getId())
        .with(user(newUserDetails)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void 팀해체_테스트() throws JsonProcessingException, Exception {
    CreateTeamRequest createTeamRequest =
        new CreateTeamRequest("FC진형", "부산시 가야동의 축구장을 거점으로 하는 국내 최대의 풋살 클럽 FC진형입니다.");
    CreateTeamResponse team = teamService.createNewTeam(member.getId(),
        createTeamRequest.getTeamName(), createTeamRequest.getTeamIntroduce());

    mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + team.getTeamId())
        .with(user(userDetails)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @Test
  public void 팀권한수정_테스트() throws JsonProcessingException, Exception {
    CreateTeamRequest createTeamRequest =
        new CreateTeamRequest("FC진형", "부산시 가야동의 축구장을 거점으로 하는 국내 최대의 풋살 클럽 FC진형입니다.");
    CreateTeamResponse team = teamService.createNewTeam(member.getId(),
        createTeamRequest.getTeamName(), createTeamRequest.getTeamIntroduce());

    MemberJoinRequest newMemberJoinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dddd")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("dddd@naver.com").phone("010-1111-2222").type(MemberType.NORMAL).build();
    QueryMemberDto newMember = memberService.join(newMemberJoinRequest);

    teamService.applyToTeam(team.getTeamId(), newMember.getId(), "화이팅!!");
    teamService.acceptApplication(team.getTeamId(), newMember.getId());
    TeamMemberType newMemberType = TeamMemberType.ADMIN;
    mvc.perform(MockMvcRequestBuilders
        .patch(BASE_URL + "/" + team.getTeamId() + "/member/" + newMember.getId())
        .with(user(userDetails)).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(newMemberType)).with(csrf())).andExpect(status().isOk())
        .andDo(print());
  }

  @SneakyThrows
  @Test
  public void updateProfileTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    memberService.join(memberADto);
    teamService.createNewTeam(memberADto.getId(), "teamA", "팀 A 소개");
    QueryTeamDto findTeam = teamService.findTeamByName("teamA");
    Long teamId = findTeam.getId();
    String introduce = "수정된 팀 소개 입니다.";
    File file = new File("src/test/resources/test.JPG");
    String mimeType = Files.probeContentType(file.toPath());
    UserDetails userDetails = userDetailsService.loadUserByUsername(memberADto.getId());
    MockMultipartFile image = new MockMultipartFile("profileImg", "test.JPG", mimeType,
        new FileInputStream(new File("src/test/resources/test.JPG")));
    mvc.perform(multipart("/api/team/profile").file(image).with(user(userDetails))
        .param("teamId", Long.toString(teamId)).param("introduce", introduce))
        .andExpect(status().isOk()).andDo(print());

    QueryTeamDto team = teamService.findTeam(findTeam.getId());
    Assertions.assertEquals(introduce, team.getIntroduce());
    log.info("team Profile Image URI = {}", team.getProfileImgUri());
  }

  @SneakyThrows
  @Test
  public void updateProfileImageTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.NORMAL).build();
    memberService.join(memberADto);
    teamService.createNewTeam(memberADto.getId(), "teamA", "팀 A 소개");
    QueryTeamDto findTeam = teamService.findTeamByName("teamA");
    Long teamId = findTeam.getId();
    String introduce = "수정된 팀 소개 입니다.";
    File file = new File("src/test/resources/test.JPG");
    String mimeType = Files.probeContentType(file.toPath());
    UserDetails userDetails = userDetailsService.loadUserByUsername(memberADto.getId());
    MockMultipartFile image = new MockMultipartFile("profileImg", "test.JPG", mimeType,
        new FileInputStream(new File("src/test/resources/test.JPG")));
    MockMultipartFile afterImage = new MockMultipartFile("profileImg", "test2.JPG", mimeType,
        new FileInputStream(new File("src/test/resources/test2.JPG")));

    mvc.perform(multipart("/api/team/profile").file(image).with(user(userDetails))
        .param("teamId", Long.toString(teamId)).param("introduce", introduce))
        .andExpect(status().isOk()).andDo(print());
    QueryTeamDto beforeTeam = teamService.findTeam(findTeam.getId());
    mvc.perform(multipart("/api/team/profile").file(afterImage).with(user(userDetails))
        .param("teamId", Long.toString(teamId)).param("introduce", introduce))
        .andExpect(status().isOk()).andDo(print());

    QueryTeamDto afterTeam = teamService.findTeam(findTeam.getId());
    Assertions.assertEquals(introduce, afterTeam.getIntroduce());
    Assertions.assertEquals(beforeTeam.getProfileImgUri(), afterTeam.getProfileImgUri());
    log.info("team Profile Image URI = {}", afterTeam.getProfileImgUri());
  }
}
