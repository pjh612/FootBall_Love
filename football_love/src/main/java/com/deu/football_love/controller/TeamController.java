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

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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
        Team findTeam = teamService.findTeam(teamName);
        if (findTeam == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().body(teamService.getTeamInfo(findTeam));
    }

    /**
     * 팀 이름 중복 체크
     **/
    @PostMapping("/duplication/{teamName}")
    public ResponseEntity duplicationCheck(@PathVariable String teamName)
    {
        Team findTeam = teamService.findTeam(teamName);
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
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        if (sessionMember == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Team newTeam = new Team();
        TeamMember teamMember = new TeamMember(newTeam, sessionMember, AuthorityType.LEADER);
        newTeam.setName(request.getName());
        newTeam.setCreateDate(LocalDate.now());
        teamMember.setTeam(newTeam);
        teamMember.setMember(sessionMember);
        newTeam.getTeamMembers().add(teamMember);
        teamService.createNewTeam(newTeam);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 가입 신청
     **/
    @PostMapping("/join_requestion/{teamName}")
    public ResponseEntity apply(@PathVariable String teamName, JoinApplyRequest request, HttpSession session)
    {
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        if (sessionMember == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Team team = teamService.findTeam(teamName);
        String message = request.getMessage();

        if(team == null || message == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        ApplicationJoinTeam application = new ApplicationJoinTeam(team, sessionMember, message);
        team.getApplicationJoinTeams().add(application);
        teamService.applyToTeam(application);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  팀 가입 수락
     */
    @PostMapping("/join_acception/{teamName}/{memberId]")
    public ResponseEntity join(@PathVariable String teamName, @PathVariable String memberId, HttpSession session)
    {
        Member newMember = memberService.findMember(memberId);
        Team findTeam = teamService.findTeam(teamName);
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        AuthorityType authorityType = teamService.authorityCheck(teamName, sessionMember.getId());
        if (authorityType != AuthorityType.LEADER && authorityType != AuthorityType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if(findTeam == null || newMember == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        ApplicationJoinTeam findApplication = teamService.findApplication(findTeam.getName(), newMember.getId());
        if (findApplication == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        TeamMember newTeamMember = new TeamMember(findTeam, newMember, AuthorityType.MEMBER);
        findTeam.getTeamMembers().add(newTeamMember);
        sessionMember.getTeamMembers().add(newTeamMember);
        teamService.acceptApplication(findApplication, newTeamMember);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 팀 멤버 탈퇴
     */
    @DeleteMapping("/{teamName}/member/{memberId}")
    public ResponseEntity withdrawal(@PathVariable String teamName, @PathVariable String memberId, HttpSession session)
    {

        Member findMember = memberService.findMember(memberId); // 추방될 멤버
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        Team findTeam = teamService.findTeam(teamName);

        if(findTeam == null || findMember == null || teamService.authorityCheck(teamName, memberId) != AuthorityType.MEMBER)
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
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        Team findTeam = teamService.findTeam(teamName);
        List<TeamMember> teamMember = teamService.findTeamMember(teamName, sessionMember.getId());
        if (teamMember.size() == 0 || teamMember.get(0).getAuthority() == AuthorityType.MEMBER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        teamService.disbandmentTeam(findTeam);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  팀 멤버 권한 수정
     */
    @PutMapping("/{teamName}/member/{memberId}")
    public ResponseEntity updateAuthority(@PathVariable String teamName, @PathVariable String memberId, @RequestBody AuthorityType authorityType, HttpSession session)
    {
        //어드민인지 확인
        Member sessionMember = memberService.findMember(((Member) session.getAttribute("memberInfo")).getId());
        AuthorityType myAuthorityType = teamService.authorityCheck(teamName, sessionMember.getId());
        if(myAuthorityType != AuthorityType.ADMIN && myAuthorityType != AuthorityType.LEADER)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        //존재하는 팀원인지 확인
        List<TeamMember> findTeamMember = teamService.findTeamMember(teamName, memberId);
        if (findTeamMember.size() == 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        teamService.updateAuthority(findTeamMember.get(0), authorityType);
        return new ResponseEntity(HttpStatus.OK);
    }
}
