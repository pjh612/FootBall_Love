package com.deu.football_love.controller;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    @PostMapping("/add")
    public ResponseEntity add(String boardName, BoardType boardType, String teamName)
    {
       if(boardName == null || boardType == null || (boardType != BoardType.GENERAL
               && boardType!= BoardType.NOTICE && boardType != BoardType.PHOTO)  || teamName == null)
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       Board board = new Board(boardName,boardType);
       boardService.newBoard(board,teamName);
       return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(Long boardId)
    {
        if(boardService.deleteBoard(boardId))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
