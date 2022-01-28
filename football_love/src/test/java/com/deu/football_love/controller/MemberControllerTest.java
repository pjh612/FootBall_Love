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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserDetailsService userDetailsService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void join() throws Exception {
        Address address = new Address("busan", "guemgangro", "46233");
        MemberJoinRequest request = new MemberJoinRequest("pjh612","123","jhjh","박진형",
               null, address, "pjh_jn@naver.com","010-1234-5678", MemberType.NORMAL);

        mvc.perform(MockMvcRequestBuilders.post("/api/member")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(request.getId())))
                .andExpect( jsonPath("$.name",is(request.getName())))
                .andExpect( jsonPath("$.nickname",is(request.getNickname())))
                .andExpect( jsonPath("$.address.city",is(request.getAddress().getCity())))
                .andExpect( jsonPath("$.address.street",is(request.getAddress().getStreet())))
                .andExpect( jsonPath("$.address.zipcode",is(request.getAddress().getZipcode())))
                .andExpect( jsonPath("$.birth",is(request.getBirth())))
                .andExpect( jsonPath("$.email",is(request.getEmail())))
                .andExpect( jsonPath("$.phone",is(request.getPhone())))
                .andExpect( jsonPath("$.type",is(request.getType().toString())))
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

        mvc.perform(MockMvcRequestBuilders.post("/api/member/login_jwt/"+ request.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(loginRequest))
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
        ).andExpect(status().isOk())
                .andDo(print());

        QueryTeamDto findTeam = teamService.findTeamByName(createTeamRequest.getName());
        QueryMemberDto memberA = memberService.findMemberById("pjh612");
        assertNotNull(findTeam);
        assertEquals(1, teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
    }
}