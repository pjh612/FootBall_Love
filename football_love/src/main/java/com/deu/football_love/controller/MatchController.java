package com.deu.football_love.controller;

import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.CreateMatchRequest;
import com.deu.football_love.dto.MemberDto;
import com.deu.football_love.service.MatchService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final MemberService memberService;
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity add(HttpSession session, CreateMatchRequest request)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        AuthorityType authorityType = teamService.authorityCheck(request.getTeamName(), sessionMember.getId());
        if (sessionMember == null || (authorityType != AuthorityType.ADMIN && authorityType != AuthorityType.LEADER))
        {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

    }

}
