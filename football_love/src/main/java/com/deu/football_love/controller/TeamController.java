package com.deu.football_love.controller;

import com.deu.football_love.controller.consts.SessionConst;
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
    @GetMapping("/{teamId}")
    public ResponseEntity get(@PathVariable Long teamId){

        return ResponseEntity.ok().body(teamService.getTeamInfo(teamId));
    }

    /**
     * 팀 이름 중복 체크
     **/
    @PostMapping("/duplication/{teamId}")
    public ResponseEntity duplicationCheck(@PathVariable Long teamId)
    {
        TeamDto findTeam = teamService.findTeam(teamId);
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
        MemberResponse sessionMember = memberService.findMember(((MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER)).getId());
        if (sessionMember == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        CreateTeamResponse response = teamService.createNewTeam(sessionMember.getId(), request.getName());
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 팀 가입 신청
     **/
    @PostMapping("/join_requestion/{teamId}")
    public ResponseEntity apply(@PathVariable Long teamId, JoinApplyRequest request, HttpSession session)
    {
        MemberResponse sessionMember = memberService.findMember(((MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER)).getId());
        if (sessionMember == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        teamService.applyToTeam(teamId,sessionMember.getName(), request.getMessage());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  팀 가입 수락
     */
    @PostMapping("/join_acception/{teamId}/{memberId]")
    public ResponseEntity join(@PathVariable Long teamId, @PathVariable String memberId, HttpSession session)
    {
        MemberResponse sessionMember = memberService.findMember(((MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER)).getId());
        AuthorityType authorityType = teamService.authorityCheck(teamId, sessionMember.getId());
        if (authorityType != AuthorityType.LEADER && authorityType != AuthorityType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        /*if(findTeam == null || newMember == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);*/
        ApplicationJoinTeamDto findApplication = teamService.findApplication(teamId, memberId);
        if (findApplication == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        AcceptApplicationResponse response = teamService.acceptApplication(teamId, memberId);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 팀 멤버 탈퇴
     */
    @DeleteMapping("/{teamId}/member/{memberId}")
    public ResponseEntity withdrawal(@PathVariable Long teamId, @PathVariable String memberId, HttpSession session)
    {
        MemberResponse sessionMember = memberService.findMember(((MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER)).getId());
        TeamDto findTeam = teamService.findTeam(teamId);

        if(findTeam == null || teamService.authorityCheck(teamId, memberId) != AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (!memberId.equals(sessionMember.getId())) // 관리자에 의해 강퇴
        {
            AuthorityType authorityType = teamService.authorityCheck(teamId, sessionMember.getId());
            if (authorityType != AuthorityType.ADMIN)
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        teamService.withdrawal(findTeam.getId(), memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 해체
     */
    @DeleteMapping("/{teamId}")
    public ResponseEntity disbandment(@PathVariable Long teamId, HttpSession session)
    {
        MemberResponse sessionMember = memberService.findMember(((MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER)).getId());
        TeamDto findTeam = teamService.findTeam(teamId);
        List<TeamMemberDto> findTeamMember = teamService.findTeamMember(teamId, sessionMember.getId());
        if (findTeamMember.size() == 0 || findTeamMember.get(0).getAuthority() == AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        DisbandmentTeamResponse response = teamService.disbandmentTeam(findTeam.getId());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     *  팀 멤버 권한 수정
     */
    @PatchMapping("/{teamId}/member/{memberId}")
    public ResponseEntity updateAuthority(@PathVariable Long teamId, @PathVariable String memberId, @RequestBody AuthorityType authorityType, HttpSession session)
    {
        //어드민인지 확인
        MemberResponse sessionMember = memberService.findMember(((MemberResponse) session.getAttribute(SessionConst.SESSION_MEMBER)).getId());
        AuthorityType myAuthorityType = teamService.authorityCheck(teamId, sessionMember.getId());
        if(myAuthorityType != AuthorityType.ADMIN && myAuthorityType != AuthorityType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        //존재하는 팀원인지 확인
        UpdateAuthorityResponse response = teamService.updateAuthority(teamId, memberId, authorityType);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
