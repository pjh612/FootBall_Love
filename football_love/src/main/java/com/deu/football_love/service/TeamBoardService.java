package com.deu.football_love.service;

import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.DeleteBoardResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.board.BoardDto;
import com.deu.football_love.repository.BoardRepository;
import com.deu.football_love.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final TeamRepository teamRepository;

    public AddBoardResponse add(AddBoardRequest request) {
        if (boardRepository.existsByTeamIdAndMemberId(request.getTeamId(),request.getBoardName()))
            throw new IllegalArgumentException("There is already board with the same name.");
        Team findTeam = teamRepository.findById(request.getTeamId()).orElseThrow(()->new IllegalArgumentException("no such Team data"));
        Board board = new Board();
        board.setBoardName(request.getBoardName());
        board.setBoardType(request.getBoardType());
        board.setTeam(findTeam);
        findTeam.getBoards().add(board);
        boardRepository.save(board);

        return AddBoardResponse.from(board);
    }

    public DeleteBoardResponse delete(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("no such board data"));
        List<Post> posts = findBoard.getPosts();
        for (Post post : posts) {
            post.setBoard(null);
        }
        posts.clear();
        boardRepository.delete(findBoard);
        return new DeleteBoardResponse(boardId, 200, "삭제 완료");
    }

    public BoardDto findById(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("no such board data"));
        if (findBoard == null)
            return null;
        return new BoardDto(findBoard);
    }

    public BoardDto findByTeamIdAndBoardName(Long teamId, String boardName) {
        Board board = boardRepository.findByTeamIdAndBoardName(boardName, teamId).orElseThrow(()-> new IllegalArgumentException("no such board data"));;
        if (board == null)
            return null;
        return new BoardDto(board);
    }


}
