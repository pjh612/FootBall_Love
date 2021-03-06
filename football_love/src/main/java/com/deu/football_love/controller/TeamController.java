package com.deu.football_love.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deu.football_love.config.JwtTokenProvider;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.team.AcceptApplicationResponse;
import com.deu.football_love.dto.team.ApplicationJoinTeamDto;
import com.deu.football_love.dto.team.CreateTeamRequest;
import com.deu.football_love.dto.team.CreateTeamResponse;
import com.deu.football_love.dto.team.DisbandmentTeamResponse;
import com.deu.football_love.dto.team.JoinApplyRequest;
import com.deu.football_love.dto.team.QueryTeamDto;
import com.deu.football_love.dto.team.QueryTeamMemberDto;
import com.deu.football_love.dto.team.UpdateAuthorityResponse;
import com.deu.football_love.dto.team.UpdateTeamProfileRequest;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/team")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TeamController {
  private final MemberService memberService;
  private final TeamService teamService;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * ??? ?????? ??????
   **/
  @GetMapping("/{teamId}")
  public ResponseEntity get(@PathVariable Long teamId,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    return ResponseEntity.ok().body(teamService.getTeamInfo(teamId));
  }

  /**
   * ??? ?????? ?????? ??????
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
   * ??? ??????
   **/
  @PostMapping
  public ResponseEntity add(@Valid @RequestBody CreateTeamRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    CreateTeamResponse response = teamService.createNewTeam(loginInfo.getId(),
        request.getTeamName(), request.getTeamIntroduce());
    if (response == null)
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  /**
   * ??? ?????? ??????
   **/
  @PostMapping("/join_requestion/{teamId}")
  public ResponseEntity apply(@PathVariable Long teamId,
      @Valid @RequestBody JoinApplyRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    if (!loginInfo.isLoggedIn())
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    teamService.applyToTeam(teamId, loginInfo.getId(), request.getMessage());
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * ??? ?????? ??????
   */
  @PostMapping("/join_acception/{teamId}/{memberId}")
  public ResponseEntity join(@PathVariable Long teamId, @PathVariable String memberId,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    TeamMemberType authorityType = teamService.authorityCheck(teamId, loginInfo.getNumber());
    if (authorityType != TeamMemberType.LEADER && authorityType != TeamMemberType.LEADER)
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    /*
     * if(findTeam == null || newMember == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
     */
    ApplicationJoinTeamDto findApplication = teamService.findApplication(teamId, memberId);
    if (findApplication == null)
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    AcceptApplicationResponse response = teamService.acceptApplication(teamId, memberId);
    if (response == null)
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  /**
   * ??? ?????? ??????
   */
  @DeleteMapping("/{teamId}/member/{memberId}")
  public ResponseEntity withdrawal(@PathVariable Long teamId, @PathVariable String memberId,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryTeamDto findTeam = teamService.findTeam(teamId);
    QueryTeamMemberDto findTeamMember =
        teamService.findTeamMemberByTeamIdAndMemberId(teamId, memberId);
    if (!memberId.equals(loginInfo.getId())) // ???????????? ?????? ??????
    {
      TeamMemberType authorityType = teamService.authorityCheck(teamId, loginInfo.getNumber());
      if (authorityType != TeamMemberType.ADMIN)
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    teamService.withdrawal(findTeam.getId(), memberId);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * ??? ??????
   */
  @DeleteMapping("/{teamId}")
  public ResponseEntity disbandment(@PathVariable Long teamId,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    QueryTeamDto findTeam = teamService.findTeam(teamId);
    QueryTeamMemberDto findTeamMember =
        teamService.findTeamMemberByTeamIdAndMemberNumber(teamId, loginInfo.getNumber());
    if (findTeamMember.getAuthority() == TeamMemberType.COMMON)
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    DisbandmentTeamResponse response = teamService.disbandmentTeam(findTeam.getId());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  /**
   * ??? ?????? ?????? ??????
   */
  @PatchMapping("/{teamId}/member/{memberId}")
  public ResponseEntity updateAuthority(@PathVariable Long teamId, @PathVariable String memberId,
      @RequestBody TeamMemberType authorityType, @AuthenticationPrincipal LoginInfo loginInfo) {
    // ??????????????? ??????
    TeamMemberType myAuthorityType = teamService.authorityCheck(teamId, loginInfo.getNumber());
    if (myAuthorityType != TeamMemberType.ADMIN && myAuthorityType != TeamMemberType.LEADER)
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    // ???????????? ???????????? ??????
    UpdateAuthorityResponse response = teamService.updateAuthority(teamId, memberId, authorityType);
    if (response == null)
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @GetMapping("/list")
  public ResponseEntity getMyTeamList(@AuthenticationPrincipal LoginInfo loginInfo) {
    return new ResponseEntity(teamService.findAllTeamByMemberNumber(loginInfo.getNumber()),
        HttpStatus.OK);
  }

  /**
   * ??? ????????? ??????
   */
  @PostMapping("/profile")
  public ResponseEntity updateTeamProfile(@ModelAttribute UpdateTeamProfileRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    TeamMemberType authority =
        teamService.authorityCheck(request.getTeamId(), loginInfo.getNumber());
    if (authority != TeamMemberType.LEADER)
      return new ResponseEntity(HttpStatus.FORBIDDEN);
    teamService.updateTeamProfile(request.getTeamId(), request.getProfileImg(),
        request.getIntroduce());
    return new ResponseEntity(HttpStatus.OK);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity handleAccessDeniedException(final IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
