package com.deu.football_love.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

  private final MemberJoinRequest JOIN_REQUEST = new MemberJoinRequest("pjh612", "123", "jhjh",
      "박진형", BIRTH_DAY, ADDRESS, "pjh_jn@naver.com", "010-1234-5678", MemberType.NORMAL);


  @DisplayName("정상적인 회원가입 테스트")
  @Test
  public void join() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(JOIN_REQUEST)).with(csrf())).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(JOIN_REQUEST.getId())))
        .andExpect(jsonPath("$.name", is(JOIN_REQUEST.getName())))
        .andExpect(jsonPath("$.nickname", is(JOIN_REQUEST.getNickname())))
        .andExpect(jsonPath("$.address.city", is(JOIN_REQUEST.getAddress().getCity())))
        .andExpect(jsonPath("$.address.street", is(JOIN_REQUEST.getAddress().getStreet())))
        .andExpect(jsonPath("$.address.zipcode", is(JOIN_REQUEST.getAddress().getZipcode())))
        .andExpect(jsonPath("$.birth", is("1995-05-02")))
        .andExpect(jsonPath("$.email", is(JOIN_REQUEST.getEmail())))
        .andExpect(jsonPath("$.phone", is(JOIN_REQUEST.getPhone())))
        .andExpect(jsonPath("$.type", is("NORMAL"))).andDo(print());
  }

  @DisplayName("파라미터중 null이 있는 회원가입 테스트")
  @Test
  public void testNotNullJoin() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(JOIN_REQUEST)).with(csrf()))
        .andExpect(status().isBadRequest()).andDo(print());
  }

  @DisplayName("휴대폰 번호의 정규표현식이 알맞지 않은 테스트")
  @Test
  public void testPhoneRegexpJoin() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(JOIN_REQUEST)).with(csrf()))
        .andExpect(status().isBadRequest()).andDo(print());
  }

  @DisplayName("빈 문자열이 들어오는 테스트")
  @Test
  public void testEmptyStringArgsJoin() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(JOIN_REQUEST)).with(csrf()))
        .andExpect(status().isBadRequest()).andDo(print());
  }

  @DisplayName("로그인,로그아웃 테스트")
  @Test
  public void loginTest() throws Exception {
    LoginRequest loginRequest = new LoginRequest(JOIN_REQUEST.getId(), JOIN_REQUEST.getPwd());
    QueryMemberDto join = memberService.join(JOIN_REQUEST);

    MvcResult loginResponse = mvc
        .perform(MockMvcRequestBuilders.post(BASE_URL + "/login_jwt/" + join.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(loginRequest)).with(csrf()))
        .andExpect(status().isOk()).andDo(print()).andReturn();

    final String accessToken = loginResponse.getResponse().getCookie(ACCESS_TOKEN).getValue();
    final String refreshToken = loginResponse.getResponse().getCookie(REFRESH_TOKEN).getValue();
    Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

    mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/logout_jwt/")
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(loginRequest))
        .cookie(accessTokenCookie).cookie(refreshTokenCookie).with(csrf()))
        .andExpect(status().isOk()).andDo(print()).andReturn();

    assertNull(redisService.getStringValue(refreshToken));
    assertNotNull(redisService.getStringValue(accessToken));
  }

  @DisplayName("email중복확인 테스트")
  @Test
  public void chkEmailDuplication() throws Exception {
    QueryMemberDto join = memberService.join(JOIN_REQUEST);
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
    QueryMemberDto join = memberService.join(JOIN_REQUEST);
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

    QueryMemberDto join = memberService.join(JOIN_REQUEST);
    UpdateMemberRequest updateRequest =
        UpdateMemberRequest.builder().pwd("1111").nickname("updatedNickName").address(ADDRESS)
            .email("updatedEmail").phone("updatedPhone").type(MemberType.NORMAL).build();
    mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + join.getId())
        .contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(status().isOk())
        .andDo(print());


  }

  @DisplayName("회원탈퇴 요청")
  @Test
  public void withdrawMemberTest() throws Exception {

    QueryMemberDto join = memberService.join(JOIN_REQUEST);
    mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + join.getId())
        .contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(status().isOk())
        .andDo(print());


  }
}

