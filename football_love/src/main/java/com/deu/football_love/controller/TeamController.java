package com.deu.football_love.controller;

import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.team.*;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/team")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TeamController {
    private final MemberService memberService;
    private final TeamService teamService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 팀 정보 조회
     **/
    @GetMapping("/{teamId}")
    public ResponseEntity get(@PathVariable Long teamId, @AuthenticationPrincipal LoginInfo loginInfo) {
        return ResponseEntity.ok().body(teamService.getTeamInfo(teamId));
    }

    /**
     * 팀 이름 중복 체크
     **/
    @PostMapping("/duplication/name")
    public ResponseEntity duplicationCheck(@RequestBody String teamName) {
        QueryTeamDto findTeam = teamService.findTeamByName(teamName);
        if (findTeam == null)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

    /**
     * 팀 생성
     **/
    @PostMapping
    public ResponseEntity add(@Valid @RequestBody CreateTeamRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
        CreateTeamResponse response = teamService.createNewTeam(loginInfo.getId(), request.getName());
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 팀 가입 신청
     **/
    @PostMapping("/join_requestion/{teamId}")
    public ResponseEntity apply(@PathVariable Long teamId, @Valid @RequestBody JoinApplyRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
        if (!loginInfo.isLoggedIn())
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        teamService.applyToTeam(teamId, loginInfo.getId(), request.getMessage());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 가입 수락
     */
    @PostMapping("/join_acception/{teamId}/{memberId]")
    public ResponseEntity join(@PathVariable Long teamId, @PathVariable String memberId, @AuthenticationPrincipal LoginInfo loginInfo) {
        TeamMemberType authorityType = teamService.authorityCheck(teamId, loginInfo.getNumber());
        if (authorityType != TeamMemberType.LEADER && authorityType != TeamMemberType.LEADER)
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
    public ResponseEntity withdrawal(@PathVariable Long teamId, @PathVariable String memberId, @AuthenticationPrincipal LoginInfo loginInfo) {
        QueryTeamDto findTeam = teamService.findTeam(teamId);

        if (findTeam == null || teamService.findTeamMemberByMemberId(teamId, memberId) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (!memberId.equals(loginInfo.getId())) // 관리자에 의해 강퇴
        {
            TeamMemberType authorityType = teamService.authorityCheck(teamId, loginInfo.getNumber());
            if (authorityType != TeamMemberType.ADMIN)
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        teamService.withdrawal(findTeam.getId(), memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 해체
     */
    @DeleteMapping("/{teamId}")
    public ResponseEntity disbandment(@PathVariable Long teamId, @AuthenticationPrincipal LoginInfo loginInfo) {
        QueryTeamDto findTeam = teamService.findTeam(teamId);
        List<QueryTeamMemberDto> findTeamMember = teamService.findTeamMember(teamId, loginInfo.getNumber());
        if (findTeamMember.size() == 0 || findTeamMember.get(0).getAuthority() == TeamMemberType.COMMON)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        DisbandmentTeamResponse response = teamService.disbandmentTeam(findTeam.getId());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 팀 멤버 권한 수정
     */
    @PatchMapping("/{teamId}/member/{memberId}")
    public ResponseEntity updateAuthority(@PathVariable Long teamId, @PathVariable String memberId, @RequestBody TeamMemberType authorityType, @AuthenticationPrincipal LoginInfo loginInfo) {
        //어드민인지 확인
        TeamMemberType myAuthorityType = teamService.authorityCheck(teamId, loginInfo.getNumber());
        if (myAuthorityType != TeamMemberType.ADMIN && myAuthorityType != TeamMemberType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        //존재하는 팀원인지 확인
        UpdateAuthorityResponse response = teamService.updateAuthority(teamId, memberId, authorityType);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
