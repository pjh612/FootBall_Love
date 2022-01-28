package com.deu.football_love.controller;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.dto.auth.LoginRequest;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import com.deu.football_love.dto.team.CreateTeamRequest;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.DisplayName;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;

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
    private ObjectMapper mapper;

    private static final String BASE_URL = "/api/member";
    
    @DisplayName("정상적인 회원가입 테스트")
    @Test
    public void join() throws Exception {

        Address address = new Address("양산", "서들4길", "18");
        MemberJoinRequest request = new MemberJoinRequest("dbtlwns","123","ggongchi","유시준",
               LocalDate.of(1995, 05, 02), address, "simba0502@naver.com","010-6779-3476", MemberType.NORMAL);
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .with(csrf())
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(request.getId())))
                .andExpect( jsonPath("$.name",is(request.getName())))
                .andExpect( jsonPath("$.nickname",is(request.getNickname())))
                .andExpect( jsonPath("$.address.city",is(request.getAddress().getCity())))
                .andExpect( jsonPath("$.address.street",is(request.getAddress().getStreet())))
                .andExpect( jsonPath("$.address.zipcode",is(request.getAddress().getZipcode())))
                .andExpect( jsonPath("$.birth",is("1995-05-02")))
                .andExpect( jsonPath("$.email",is(request.getEmail())))
                .andExpect( jsonPath("$.phone",is(request.getPhone())))
                .andExpect( jsonPath("$.type",is("NORMAL")))
                .andDo(print());
    }

    @DisplayName("파라미터중 null이 있는 회원가입 테스트")
    @Test
    public void testNotNullJoin() throws Exception {
        Address address = new Address("양산", "서들4길", "18");
        MemberJoinRequest request = new MemberJoinRequest("dbtlwns","123","ggongchi",null,
               LocalDate.of(1995, 05, 02), address, "simba0502@naver.com","010-6779-3476", MemberType.NORMAL);
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .with(csrf())
        ).andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @DisplayName("휴대폰 번호의 정규표현식이 알맞지 않은 테스트")
    @Test
    public void testPhoneRegexpJoin() throws Exception {
        Address address = new Address("양산", "서들4길", "18");
        MemberJoinRequest request = new MemberJoinRequest("dbtlwns","123","ggongchi","유시준",
               LocalDate.of(1995, 05, 02), address, "simba0502@naver.com","01067793476", MemberType.NORMAL);
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .with(csrf())
        ).andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @DisplayName("빈 문자열이 들어오는 테스트")
    @Test
    public void testEmptyStringArgsJoin() throws Exception {
        Address address = new Address("양산", "서들4길", "18");
        MemberJoinRequest request = new MemberJoinRequest("","123","ggongchi","유시준",
               LocalDate.of(1995, 05, 02), address, "simba0502@naver.com","01067793476", MemberType.NORMAL);
        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .with(csrf())
        ).andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @Test
    public void login() throws Exception {
        Address address = new Address("busan", "guemgangro", "46233");
        mapper.registerModule(new JavaTimeModule());
        MemberJoinRequest request = new MemberJoinRequest("pjh612","123","jhjh","박진형",
                null, address, "pjh_jn@naver.com","01021042419", MemberType.NORMAL);

        LoginRequest loginRequest = new LoginRequest(request.getId(), request.getPwd());
        memberService.join(request);


        mvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/login_jwt/"+ request.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(loginRequest))
                .with(csrf())
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createTeam() throws Exception {

        Address address = new Address("busan", "guemgangro", "46233");
        mapper.registerModule(new JavaTimeModule());
        MemberJoinRequest request = new MemberJoinRequest("pjh612","123","jhjh","박진형",
                null, address, "pjh_jn@naver.com","01021042419", MemberType.NORMAL);
        QueryMemberDto join = memberService.join(request);
        CreateTeamRequest createTeamRequest = new CreateTeamRequest("FC진형");
        UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());
        mvc.perform(MockMvcRequestBuilders.post("/api/team").with(user(userDetails))
                .contentType("application/json")
                .content(mapper.writeValueAsString(createTeamRequest))
                .with(csrf())
        ).andExpect(status().isOk())
                .andDo(print());

        QueryTeamDto findTeam = teamService.findTeamByName(createTeamRequest.getName());
        QueryMemberDto memberA = memberService.findMemberById("pjh612");
        assertNotNull(findTeam);
        assertEquals(1, teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
    }
}