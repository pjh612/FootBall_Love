package com.deu.football_love.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.match.AddMatchResponse;
import com.deu.football_love.dto.match.ApproveMatchRequest;
import com.deu.football_love.dto.match.CreateMatchRequest;
import com.deu.football_love.dto.match.MatchApplicationResponse;
import com.deu.football_love.dto.match.MatchApproveResponse;
import com.deu.football_love.dto.match.ModifyMatchRequest;
import com.deu.football_love.dto.match.ModifyMatchResponse;
import com.deu.football_love.dto.match.RemoveRequest;
import com.deu.football_love.dto.match.ApplyMatchRequest;
import com.deu.football_love.service.MatchServiceImpl;
import com.deu.football_love.service.MemberService;
import com.deu.football_love.service.TeamService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

	private final MatchServiceImpl matchService;

	private final MemberService memberService;

	private final TeamService teamService;

	@ApiOperation("매치생성")
	@PostMapping("/create")
	public ResponseEntity<AddMatchResponse> add(@RequestBody CreateMatchRequest request, @AuthenticationPrincipal LoginInfo loginInfo ) {
		if (checkAuthority(request.getTeamId(), loginInfo.getNumber())) {
			return new ResponseEntity<AddMatchResponse>(HttpStatus.FORBIDDEN);
		}
		AddMatchResponse matchResponse = matchService.addMatch(request.getTeamId(), request.getStadiumId(),
				request.getReservation_time());
		return new ResponseEntity<AddMatchResponse>(matchResponse, HttpStatus.OK);
	}

	@ApiOperation("매치확정")
	@PostMapping("/approval/{matchId}")
	public ResponseEntity<MatchApproveResponse> approve(@RequestBody ApproveMatchRequest request,
			@PathVariable("matchId") Long matchId, @AuthenticationPrincipal LoginInfo loginInfo) {
		if (checkAuthority(request.getTeamId(), loginInfo.getNumber())) {
			return new ResponseEntity<MatchApproveResponse>(HttpStatus.FORBIDDEN);
		}
		MatchApproveResponse matchApproveResponse = matchService.approveMatch(matchId, request.getMatchApplicationId());
		return new ResponseEntity<MatchApproveResponse>(matchApproveResponse, HttpStatus.OK);
	}

	@ApiOperation("매치신청")
	@PostMapping("/match/apply")
	public ResponseEntity<MatchApplicationResponse> apply(@RequestBody ApplyMatchRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
		if (checkAuthority(request.getTeamId(), loginInfo.getNumber())) {
			return new ResponseEntity<MatchApplicationResponse>(HttpStatus.FORBIDDEN);
		}
		MatchApplicationResponse matchResponse = matchService.addMatchApplication(request.getTeamId(), request.getMatchId());
		return new ResponseEntity<MatchApplicationResponse>(matchResponse, HttpStatus.OK);
	}

	@ApiOperation("매치삭제")
	@DeleteMapping
	public ResponseEntity cancel(@RequestBody RemoveRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
		if (checkAuthority(request.getTeamId(), loginInfo.getNumber())) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		matchService.cancelMatch(request.getMatchId());
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation("매치수정")
	@PutMapping
	public ResponseEntity modify(@RequestBody ModifyMatchRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
		if (checkAuthority(request.getTeamId(), loginInfo.getNumber())) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		ModifyMatchResponse modifyMatchResponse = matchService.modifyMatch(request.getMatchId(), request.getStadiumId(),
				request.getReservationTime());
		return new ResponseEntity<ModifyMatchResponse>(modifyMatchResponse, HttpStatus.OK);
	}

	private boolean checkAuthority(Long teamId, Long memberNumber) {
		TeamMemberType authorityType = teamService.authorityCheck(teamId, memberNumber);
		return authorityType != TeamMemberType.ADMIN && authorityType != TeamMemberType.LEADER;
	}
}
