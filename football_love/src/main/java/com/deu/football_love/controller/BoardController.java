package com.deu.football_love.controller;

import com.deu.football_love.domain.type.TeamMemberType;
import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.DeleteBoardResponse;
import com.deu.football_love.service.BoardService;
import com.deu.football_love.service.TeamService;
import com.mysql.cj.log.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final TeamService teamService;

    @PostMapping("/team/{teamId}/board")
    public ResponseEntity add(@PathVariable("teamId") Long teamId, @RequestBody AddBoardRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
        TeamMemberType teamMemberType = teamService.authorityCheck(teamId, loginInfo.getNumber());
        if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER)
            return new ResponseEntity("권한 없음", HttpStatus.FORBIDDEN);
        if (boardService.add(request) != null)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity("중복", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/team/{teamId}/board/{boardId}")
    public ResponseEntity delete(@PathVariable Long boardId, @PathVariable Long teamId, @AuthenticationPrincipal LoginInfo loginInfo) {
        TeamMemberType teamMemberType = teamService.authorityCheck(teamId, loginInfo.getNumber());
        if (teamMemberType != TeamMemberType.ADMIN && teamMemberType != TeamMemberType.LEADER)
            return new ResponseEntity(new DeleteBoardResponse(boardId, 403, "권한 없음"), HttpStatus.FORBIDDEN);
        DeleteBoardResponse response = boardService.delete(boardId);
        if (response.getStatus() != 200)
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity serverException() {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
