package com.deu.football_love.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.member.UpdateMemberRequest;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import com.deu.football_love.service.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MemberService memberService;

  @Autowired
  private TeamService teamService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private ObjectMapper mapper;

  private static final String BASE_URL = "/api/member";

  private static final String ACCESS_TOKEN = "accessToken";

  private static final String REFRESH_TOKEN = "refreshToken";

  private final LocalDate BIRTH_DAY = LocalDate.of(2000, 1, 1);

  private final Address ADDRESS = new Address("양산", "행복길", "11");

  @DisplayName("정상적인 회원가입 테스트")
  @Test
  public void join() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(joinRequest)).with(csrf())).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(joinRequest.getId())))
        .andExpect(jsonPath("$.name", is(joinRequest.getName())))
        .andExpect(jsonPath("$.nickname", is(joinRequest.getNickname())))
        .andExpect(jsonPath("$.address.city", is(joinRequest.getAddress().getCity())))
        .andExpect(jsonPath("$.address.street", is(joinRequest.getAddress().getStreet())))
        .andExpect(jsonPath("$.address.zipcode", is(joinRequest.getAddress().getZipcode())))
        .andExpect(jsonPath("$.birth", is("2000-01-01")))
        .andExpect(jsonPath("$.email", is(joinRequest.getEmail())))
        .andExpect(jsonPath("$.phone", is(joinRequest.getPhone())))
        .andExpect(jsonPath("$.type", is("ROLE_NORMAL"))).andDo(print());
  }

  @DisplayName("파라미터중 null이 있는 회원가입 테스트")
  @Test
  public void testNotNullJoin() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY).email("fblCorp@naver.com")
        .phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(joinRequest)).with(csrf()))
        .andExpect(status().isBadRequest()).andDo(print());
  }

  @DisplayName("휴대폰 번호의 정규표현식이 알맞지 않은 테스트")
  @Test
  public void testPhoneRegexpJoin() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-22221").type(MemberType.ROLE_NORMAL).build();

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(joinRequest)).with(csrf()))
        .andExpect(status().isBadRequest()).andDo(print());
  }

  @DisplayName("빈 문자열이 들어오는 테스트")
  @Test
  public void testEmptyStringArgsJoin() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(joinRequest)).with(csrf()))
        .andExpect(status().isBadRequest()).andDo(print());
  }

  @DisplayName("로그인,로그아웃 테스트")
  @Test
  public void loginTest() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    LoginRequest loginRequest = new LoginRequest(joinRequest.getId(), joinRequest.getPwd());
    QueryMemberDto join = memberService.join(joinRequest);

    MvcResult loginResponse = mvc
        .perform(MockMvcRequestBuilders.post(BASE_URL + "/login_jwt/" + join.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(loginRequest)).with(csrf()))
        .andExpect(status().isOk()).andDo(print()).andReturn();

    final String accessToken = loginResponse.getResponse().getCookie(ACCESS_TOKEN).getValue();
    final String refreshToken = loginResponse.getResponse().getCookie(REFRESH_TOKEN).getValue();
    Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

    MvcResult logoutResponse = mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/logout_jwt/")
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(loginRequest))
        .cookie(accessTokenCookie).cookie(refreshTokenCookie).with(csrf()))
        .andExpect(status().isOk()).andDo(print()).andReturn();

    Assertions.assertNull(logoutResponse.getResponse().getCookie(ACCESS_TOKEN));
    Assertions.assertNull(logoutResponse.getResponse().getCookie(REFRESH_TOKEN));
    Assertions.assertNull(redisService.getStringValue(refreshToken));
    Assertions.assertNotNull(redisService.getStringValue(accessToken));
  }

  @DisplayName("email중복확인 테스트")
  @Test
  public void chkEmailDuplication() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    QueryMemberDto join = memberService.join(joinRequest);
    mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/duplication/email")
        .contentType(MediaType.APPLICATION_JSON).param("email", join.getEmail()).with(csrf()))
        .andExpect(status().isConflict()).andDo(print());

    String newEmail = "helloWorld@naver.com";
    mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/duplication/email")
        .contentType(MediaType.APPLICATION_JSON).param("email", newEmail).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("id중복확인 테스트")
  @Test
  public void chkIdDuplication() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    QueryMemberDto join = memberService.join(joinRequest);
    mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/duplication/id")
        .contentType(MediaType.APPLICATION_JSON).param("id", join.getId()).with(csrf()))
        .andExpect(status().isConflict()).andDo(print());

    String newId = "lilililiil";
    mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/duplication/id")
        .contentType(MediaType.APPLICATION_JSON).param("id", newId).with(csrf()))
        .andExpect(status().isOk()).andDo(print());
  }

  @DisplayName("회원정보 수정 테스트")
  @Test
  public void modifyMemberInfoTest() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    QueryMemberDto join = memberService.join(joinRequest);
    UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());

    UpdateMemberRequest updateRequest =
        UpdateMemberRequest.builder().pwd("1111").nickname("updatedNickName").address(ADDRESS)
            .email("fblCorp11@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();

    mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + join.getId())
        .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)).with(csrf())
        .content(mapper.writeValueAsString(updateRequest))).andExpect(status().isOk())
        .andDo(print());


  }

  @DisplayName("회원탈퇴 요청")
  @Test
  public void withdrawMemberTest() throws Exception {
    MemberJoinRequest joinRequest = MemberJoinRequest.memberJoinRequestBuilder().id("dbtlwns")
        .name("유시준").pwd("1234").nickname("개발고수").address(ADDRESS).birth(BIRTH_DAY)
        .email("fblCorp@naver.com").phone("010-1111-2222").type(MemberType.ROLE_NORMAL).build();
    QueryMemberDto join = memberService.join(joinRequest);
    UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());
    mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/withdrawals/" + join.getId())
        .contentType(MediaType.APPLICATION_JSON).with(csrf()).with(user(userDetails)))
        .andExpect(status().isOk()).andDo(print());


  }
}

