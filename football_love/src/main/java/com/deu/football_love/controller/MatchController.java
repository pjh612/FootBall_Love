package com.deu.football_love.controller;

import com.deu.football_love.controller.consts.SessionConst;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.match.CreateMatchRequest;
import com.deu.football_love.dto.member.MemberResponse;
import com.deu.football_love.service.MatchServiceImpl;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController()
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchServiceImpl matchService;
    private final MemberService memberService;
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity add(HttpSession session, CreateMatchRequest request) {
        MemberResponse sessionMember = (MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER);
        AuthorityType authorityType = teamService.authorityCheck(request.getTeamId(), sessionMember.getNumber());
        if (sessionMember == null || (authorityType != AuthorityType.ADMIN && authorityType != AuthorityType.LEADER)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

}
