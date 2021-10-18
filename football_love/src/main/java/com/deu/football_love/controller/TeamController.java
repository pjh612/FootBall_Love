package com.deu.football_love.controller;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamAdmin;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RequestMapping("/team")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TeamController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    private final MemberService memberService;
    private final TeamService teamService;
    @PostMapping
    public ResponseEntity add(@RequestBody Map<String,String> params)
    {
        Member findMember = memberService.findMember(params.get("creator"));
        Team newTeam = new Team();
        TeamAdmin teamAdmin = new TeamAdmin();
        newTeam.setName(params.get("teamName"));
        newTeam.setCreateDate(LocalDate.now());
        teamAdmin.setTeam(newTeam);
        teamAdmin.setMember(findMember);
        teamService.createNewTeam(teamAdmin, newTeam);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody Map<String,String> params)
    {
        Member findMember = memberService.findMember(params.get("id"));
        Team team = teamService.findTeam(params.get("teamName"));

        if(team == null || findMember == null)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setMember(findMember);
        newTeamMember.setTeam(team);
        teamService.joinTeam(newTeamMember);
        return new ResponseEntity(HttpStatus.OK);
    }
}
