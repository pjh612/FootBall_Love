package com.deu.football_love.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class TeamControllerTest {
  // @Test
  // public void createTeam() throws Exception {
  //
  // Address address = new Address("busan", "guemgangro", "46233");
  // MemberJoinRequest request = new MemberJoinRequest("pjh612","123","jhjh","박진형",
  // null, address, "pjh_jn@naver.com","01021042419", MemberType.NORMAL);
  // QueryMemberDto join = memberService.join(request);
  // CreateTeamRequest createTeamRequest = new CreateTeamRequest("FC진형","반갑습니다.");
  // UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());
  // mvc.perform(MockMvcRequestBuilders.post("/api/team").with(user(userDetails))
  // .contentType("application/json")
  // .content(mapper.writeValueAsString(createTeamRequest))
  // .with(csrf())
  // ).andExpect(status().isOk())
  // .andDo(print());
  //
  // QueryTeamDto findTeam = teamService.findTeamByName(createTeamRequest.getTeamName());
  // QueryMemberDto memberA = memberService.findMemberById("pjh612");
  // assertNotNull(findTeam);
  // assertEquals(1, teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
  // }

  @Autowired
  MockMvc mvc;

  @Autowired
  TeamService teamService;

  @Autowired
  MemberService memberService;

  @Autowired
  UserDetailsService userDetailsService;

  @SneakyThrows
  @Test
  public void updateProfileTest() {
    MemberJoinRequest memberADto = MemberJoinRequest.memberJoinRequestBuilder().id("memberA")
        .name("유시준").pwd("1234").nickname("개발고수").address(new Address("양산", "행복길", "11"))
        .birth(LocalDate.of(2000, 1, 1)).email("fblCorp1@naver.com").phone("010-1111-2222")
        .type(MemberType.ROLE_NORMAL).build();
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
        .param("teamId", Long.toString(teamId))
        .param("introduce", introduce))
        .andExpect(status().isOk())
        .andDo(print());

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
        .type(MemberType.ROLE_NORMAL).build();
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
        .param("teamId", Long.toString(teamId))
        .param("introduce", introduce))
        .andExpect(status().isOk())
        .andDo(print());
    QueryTeamDto beforeTeam = teamService.findTeam(findTeam.getId());
    mvc.perform(multipart("/api/team/profile").file(afterImage).with(user(userDetails))
        .param("teamId", Long.toString(teamId))
        .param("introduce", introduce))
        .andExpect(status().isOk())
        .andDo(print());

    QueryTeamDto afterTeam = teamService.findTeam(findTeam.getId());
    Assertions.assertEquals(introduce, afterTeam.getIntroduce());
    Assertions.assertEquals(beforeTeam.getProfileImgUri(), afterTeam.getProfileImgUri());
    log.info("team Profile Image URI = {}", afterTeam.getProfileImgUri());
  }
}
