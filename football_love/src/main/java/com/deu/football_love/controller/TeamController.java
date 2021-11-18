package com.deu.football_love.controller;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.*;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/team")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TeamController {

    private final MemberService memberService;
    private final TeamService teamService;

    /**
     * 팀 정보 조회
     **/
    @GetMapping("/{teamName}")
    public ResponseEntity get(@PathVariable String teamName){

        return ResponseEntity.ok().body(teamService.getTeamInfo(teamName));
    }

    /**
     * 팀 이름 중복 체크
     **/
    @PostMapping("/duplication/{teamName}")
    public ResponseEntity duplicationCheck(@PathVariable String teamName)
    {
        TeamDto findTeam = teamService.findTeam(teamName);
        if(findTeam == null)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

    /**
     * 팀 생성
     **/
    @PostMapping
    public ResponseEntity add(@RequestBody CreateTeamRequest request, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        if (sessionMember == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        CreateTeamResponse response = teamService.createNewTeam(sessionMember, request.getName());
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 팀 가입 신청
     **/
    @PostMapping("/join_requestion/{teamName}")
    public ResponseEntity apply(@PathVariable String teamName, JoinApplyRequest request, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        if (sessionMember == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        teamService.applyToTeam(teamName,sessionMember.getName(), request.getMessage());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  팀 가입 수락
     */
    @PostMapping("/join_acception/{teamName}/{memberId]")
    public ResponseEntity join(@PathVariable String teamName, @PathVariable String memberId, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        AuthorityType authorityType = teamService.authorityCheck(teamName, sessionMember.getId());
        if (authorityType != AuthorityType.LEADER && authorityType != AuthorityType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        /*if(findTeam == null || newMember == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);*/
        ApplicationJoinTeamDto findApplication = teamService.findApplication(teamName, memberId);
        if (findApplication == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        AcceptApplicationResponse response = teamService.acceptApplication(teamName, memberId);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 팀 멤버 탈퇴
     */
    @DeleteMapping("/{teamName}/member/{memberId}")
    public ResponseEntity withdrawal(@PathVariable String teamName, @PathVariable String memberId, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        TeamDto findTeam = teamService.findTeam(teamName);

        if(findTeam == null || teamService.authorityCheck(teamName, memberId) != AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (!memberId.equals(sessionMember.getId())) // 관리자에 의해 강퇴
        {
            AuthorityType authorityType = teamService.authorityCheck(teamName, sessionMember.getId());
            if (authorityType != AuthorityType.ADMIN)
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        teamService.withdrawal(findTeam.getName(), memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 해체
     */
    @DeleteMapping("/{teamName}")
    public ResponseEntity disbandment(@PathVariable String teamName, HttpSession session)
    {
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        TeamDto findTeam = teamService.findTeam(teamName);
        List<TeamMemberDto> findTeamMember = teamService.findTeamMember(teamName, sessionMember.getId());
        if (findTeamMember.size() == 0 || findTeamMember.get(0).getAuthority() == AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        DisbandmentTeamResponse response = teamService.disbandmentTeam(findTeam.getName());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     *  팀 멤버 권한 수정
     */
    @PatchMapping("/{teamName}/member/{memberId}")
    public ResponseEntity updateAuthority(@PathVariable String teamName, @PathVariable String memberId, @RequestBody AuthorityType authorityType, HttpSession session)
    {
        //어드민인지 확인
        MemberDto sessionMember = memberService.findMember(((MemberDto) session.getAttribute("memberInfo")).getId());
        AuthorityType myAuthorityType = teamService.authorityCheck(teamName, sessionMember.getId());
        if(myAuthorityType != AuthorityType.ADMIN && myAuthorityType != AuthorityType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        //존재하는 팀원인지 확인
        UpdateAuthorityResponse response = teamService.updateAuthority(teamName, memberId, authorityType);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
