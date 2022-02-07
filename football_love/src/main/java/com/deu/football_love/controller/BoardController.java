package com.deu.football_love.controller;

import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.DeleteBoardResponse;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    private final TeamService teamService;

    @PostMapping("/team/{teamId}/board")
    public ResponseEntity add(@PathVariable("teamId") Long teamId,@Valid @RequestBody AddBoardRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
        TeamMemberType teamMemberType = teamService.authorityCheck(teamId, loginInfo.getNumber());
        if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER)
            return new ResponseEntity("권한 없음", HttpStatus.FORBIDDEN);
       boardService.add(request);
       return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/team/{teamId}/board/{boardId}")
    public ResponseEntity delete(@PathVariable Long boardId, @PathVariable Long teamId, @AuthenticationPrincipal LoginInfo loginInfo) {
        TeamMemberType teamMemberType = teamService.authorityCheck(teamId, loginInfo.getNumber());
        if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER)
            return new ResponseEntity(new DeleteBoardResponse(boardId, 403, "권한 없음"), HttpStatus.FORBIDDEN);
        DeleteBoardResponse response = boardService.delete(boardId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity handleAccessDeniedException(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
