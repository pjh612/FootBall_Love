package com.deu.football_love.controller;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.CreateTeamRequest;
import com.deu.football_love.dto.JoinApplyRequest;
import com.deu.football_love.dto.TeamDto;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/team")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TeamController {

    private final MemberService memberService;
    private final TeamService teamService;

    @GetMapping("/{teamName}")
    public ResponseEntity get(@PathVariable String teamName){
        Team findTeam = teamService.findTeam(teamName);
        return ResponseEntity.ok().body(teamService.getTeamInfo(findTeam));
    }

    @PostMapping("/duplication/{teamName}")
    public ResponseEntity duplicationCheck(@PathVariable String teamName)
    {
        Team findTeam = teamService.findTeam(teamName);
        if(findTeam == null)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody CreateTeamRequest request)
    {
        Member findMember = memberService.findMember("");
        Team newTeam = new Team();
        TeamAdmin teamAdmin = new TeamAdmin();
        newTeam.setName(request.getName());
        newTeam.setCreateDate(LocalDate.now());
        teamAdmin.setTeam(newTeam);
        teamAdmin.setMember(findMember);
        teamService.createNewTeam(teamAdmin, newTeam);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 가입 신청
     * 세션 구현이 안되어있어서 세션 구현 후 수정 해야함
     **/
    @PostMapping("/join_requestion/{teamName}")
    public ResponseEntity apply(@PathVariable String teamName, JoinApplyRequest request)
    {
        /**
         * findMember는 세션의 로그인 회원 정보
         */
        Member findMember = memberService.findMember("");
        Team team = teamService.findTeam(teamName);
        String message = request.getMessage();

        if(team == null || findMember == null || message == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        ApplicationJoinTeam application = new ApplicationJoinTeam(team, findMember, message);

        teamService.applyToTeam(application);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  팀 가입 수락
     *  세션 구현 후 team의 admin인지 확인하고 처리해야함
     */
    @PostMapping("/join_acception/{teamName}/{memberId]")
    public ResponseEntity join(@PathVariable String teamName, @PathVariable String memberId)
    {
        Member findMember = memberService.findMember(memberId);
        Team findTeam = teamService.findTeam(teamName);

        /**
         * 어드민이 아니면 401 오류 발생
         */
        if(findTeam == null || findMember == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        ApplicationJoinTeam findApplication = teamService.findApplication(findTeam.getName(), findMember.getId());
        if (findApplication == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        TeamMember newTeamMember = new TeamMember(findTeam, findMember, AuthorityType.MEMBER);
        teamService.acceptApplication(findApplication, newTeamMember);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 멤버 탈퇴
     * 
     */
    @DeleteMapping("/{teamName}/member/{memberId}")
    public ResponseEntity withdrawal(@PathVariable String teamName, @PathVariable String memberId)
    {

        Member findMember = memberService.findMember(memberId); // 추방될 멤버
        Member loginedMember = memberService.findMember("");// 현재 계정 정보
        Team findTeam = teamService.findTeam(teamName);

        if(findTeam == null || findMember == null || teamService.authorityCheck(teamName, memberId) != AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (!memberId.equals(loginedMember.getId())) // 관리자에 의해 강퇴
        {
            AuthorityType authorityType = teamService.authorityCheck(teamName, loginedMember.getId());
            if (authorityType != AuthorityType.ADMIN)
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        teamService.withdrawal(findTeam.getName(), memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 해체
     * 
     */
    @DeleteMapping("/{teamName}")
    public ResponseEntity disbandment(@PathVariable String teamName)
    {
        Member loginedMember = memberService.findMember("");
        Team findTeam = teamService.findTeam(teamName);
        List<TeamMember> teamMember = teamService.findTeamMember(teamName, loginedMember.getId());
        if (teamMember.size() == 0 || teamMember.get(0).getAuthority() == AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        teamService.disbandmentTeam(findTeam);
        return new ResponseEntity(HttpStatus.OK);
    }
}
