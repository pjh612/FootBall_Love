package com.deu.football_love.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deu.football_love.domain.type.MemberType;
import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.Teamboard.AddTeamBoardRequest;
import com.deu.football_love.dto.Teamboard.DeleteTeamBoardResponse;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.DeleteBoardResponse;
import com.deu.football_love.dto.board.QueryBoardDto;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.TeamBoardService;
import com.deu.football_love.service.TeamService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

  private final BoardService boardService;

  private final TeamBoardService teamBoardService;

  private final TeamService teamService;

  @ApiOperation("공용 게시판 추가")
  @PostMapping("/board")
  public ResponseEntity add(@Valid @RequestBody AddBoardRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    String memberType = loginInfo.getAuthorities().iterator().next().getAuthority();
    if (!memberType.equals(MemberType.ROLE_ADMIN.name())) {
      return new ResponseEntity("권한 없음", HttpStatus.FORBIDDEN);
    }
    boardService.add(request);
    return new ResponseEntity(HttpStatus.OK);
  }

  @ApiOperation("팀 게시판 추가")
  @PostMapping("/team/{teamId}/board")
  public ResponseEntity add(@PathVariable("teamId") Long teamId, @Valid @RequestBody AddTeamBoardRequest request,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    TeamMemberType teamMemberType = teamService.authorityCheck(teamId, loginInfo.getNumber());
    if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER)
      return new ResponseEntity("권한 없음", HttpStatus.FORBIDDEN);
    teamBoardService.add(request);
    return new ResponseEntity(HttpStatus.OK);
  }

  @ApiOperation("공용 게시판 삭제")
  @DeleteMapping("/board/{boardId}")
  public ResponseEntity<DeleteBoardResponse> delete(@PathVariable Long boardId, @AuthenticationPrincipal LoginInfo loginInfo) {
    String memberType = loginInfo.getAuthorities().iterator().next().getAuthority();
    if (!memberType.equals(MemberType.ROLE_ADMIN.name())) {
      return new ResponseEntity<DeleteBoardResponse>(new DeleteBoardResponse(boardId, 403, "권한 없음"), HttpStatus.FORBIDDEN);
    }
    DeleteBoardResponse response = boardService.delete(boardId);
    return new ResponseEntity<DeleteBoardResponse>(response, HttpStatus.OK);
  }

  @ApiOperation("팀 게시판 삭제")
  @DeleteMapping("/team/{teamId}/board/{boardId}")
  public ResponseEntity<DeleteTeamBoardResponse> delete(@PathVariable Long boardId, @PathVariable Long teamId,
      @AuthenticationPrincipal LoginInfo loginInfo) {
    TeamMemberType teamMemberType = teamService.authorityCheck(teamId, loginInfo.getNumber());
    if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER)
      return new ResponseEntity<DeleteTeamBoardResponse>(new DeleteTeamBoardResponse(boardId, 403, "권한 없음"), HttpStatus.FORBIDDEN);
    DeleteTeamBoardResponse response = teamBoardService.delete(boardId);
    return new ResponseEntity<DeleteTeamBoardResponse>(response, HttpStatus.OK);
  }

  @ApiOperation("공용 게시판 목록")
  @GetMapping("/board")
  public ResponseEntity<List<QueryBoardDto>> getBoard(@AuthenticationPrincipal LoginInfo loginInfo) {
    String memberType = loginInfo.getAuthorities().iterator().next().getAuthority();
    if (!memberType.equals(MemberType.ROLE_ADMIN.name())) {
      return new ResponseEntity<List<QueryBoardDto>>(HttpStatus.FORBIDDEN);
    }
    List<QueryBoardDto> boardDtoList = boardService.get();
    return new ResponseEntity<List<QueryBoardDto>>(boardDtoList, HttpStatus.OK);
  }

}
