package com.deu.football_love.controller;

import com.deu.football_love.domain.type.MatchState;
import com.deu.football_love.dto.match.QueryMatchDto;
import com.deu.football_love.dto.match.RefuseMatchApplicationRequest;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.ApproveMatchRequest;
import com.deu.football_love.dto.match.AddMatchRequest;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.dto.match.ModifyMatchRequest;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.ApplyMatchRequest;
import com.deu.football_love.service.MatchService;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/api/match")
@RequiredArgsConstructor
@Slf4j
public class MatchController {

  private final MatchService matchService;

  private final MemberService memberService;

  private final TeamService teamService;

  @ApiOperation("매치 조회")
  @GetMapping
  public Page<QueryMatchDto> getByState(@RequestParam("state") MatchState state, Pageable pageable) {
    return matchService.findAllMatchByState(state, pageable);
  }


  @ApiOperation("매치생성")
  @PostMapping
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity<AddMatchResponse> add(@Valid @RequestBody AddMatchRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    AddMatchResponse matchResponse = matchService.addMatch(request.getStadiumId(), loginInfo.getCompanyId(), request.getReservationTime());
    return new ResponseEntity<AddMatchResponse>(matchResponse, HttpStatus.OK);
  }

  @ApiOperation("매치확정")
  @PostMapping("/approval/{matchId}")
  public ResponseEntity<MatchApproveResponse> approve(@Valid @RequestBody ApproveMatchRequest request, @PathVariable("matchId") Long matchId,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    MatchApproveResponse matchApproveResponse = matchService.approveMatchApplication(matchId, request.getMatchApplicationId(), loginInfo.getNumber());
    return new ResponseEntity<>(matchApproveResponse, HttpStatus.OK);
  }

  @ApiOperation("매치에 팀 등록")
  @PostMapping("/apply")
  public ResponseEntity registerTeam(@Valid @RequestBody ApplyMatchRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    if (!isTeamAdmin(request.getTeamId(), loginInfo.getNumber())) {
      return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
    QueryMatchDto findMatch = matchService.findMatch(request.getMatchId());
    if (findMatch.getState().equals(MatchState.EMPTY)) {
      matchService.registerTeamA(request.getMatchId(), request.getTeamId());
    } else if (findMatch.getState().equals(MatchState.WAITING)) {
      matchService.addMatchApplication(request.getMatchId(), request.getTeamId());
    }
    return new ResponseEntity(HttpStatus.OK);
  }

  @ApiOperation("매치 신청 거절")
  @PostMapping("/application/refuse")
  public ResponseEntity refuseMatchApplication(@Valid @RequestBody RefuseMatchApplicationRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    matchService.refuseMatchApplication(request.getApplicationId(), request.getRefuseMessage(), loginInfo.getNumber());
    return new ResponseEntity(HttpStatus.OK);
  }

  @ApiOperation("매치삭제")
  @DeleteMapping("/{matchId}")
  public ResponseEntity cancel(@Valid @PathVariable Long matchId, @AuthenticationPrincipal LoginInfo loginInfo) {
    matchService.cancelMatch(matchId, loginInfo.getCompanyId());
    return new ResponseEntity(HttpStatus.OK);
  }

  @ApiOperation("매치수정")
  @PutMapping
  public ResponseEntity<ModifyMatchResponse> modify(@Valid @RequestBody ModifyMatchRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    if (!isTeamAdmin(request.getTeamId(), loginInfo.getNumber())) {
      return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
    ModifyMatchResponse modifyMatchResponse = matchService.modifyMatch(request.getMatchId(), request.getStadiumId(), request.getReservationTime());
    return new ResponseEntity<>(modifyMatchResponse, HttpStatus.OK);
  }

  private boolean isTeamAdmin(Long teamId, Long memberNumber) {
    TeamMemberType authorityType = teamService.authorityCheck(teamId, memberNumber);
    return authorityType == TeamMemberType.ADMIN || authorityType == TeamMemberType.LEADER;
  }


}
