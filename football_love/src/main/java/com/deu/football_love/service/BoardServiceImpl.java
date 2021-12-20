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
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final TeamRepository teamRepository;

    @Override
    public AddBoardResponse add(AddBoardRequest request) {
       /* if (boardRepository.countBoardByType(request.getTeamId(), request.getBoardType()) > 0) {
            return null;
        }*/
        if (boardRepository.selectBoardByTeamIdAndBoardName(request.getBoardName(), request.getTeamId()) != null)
            return null;
        Team findTeam = teamRepository.selectTeam(request.getTeamId());
        if (findTeam == null)
            return null;
        Board board = new Board();
        board.setBoardName(request.getBoardName());
        board.setBoardType(request.getBoardType());
        board.setTeam(findTeam);
        findTeam.getBoards().add(board);
        boardRepository.insertBoard(board);

        return AddBoardResponse.from(board);
    }

    @Override
    public DeleteBoardResponse delete(Long boardId) {
        Board findBoard = boardRepository.selectBoardById(boardId);
        if (findBoard == null)
            return new DeleteBoardResponse(boardId, 400, "존재하지 않는 게시판");
        List<Post> posts = findBoard.getPosts();
        for (Post post : posts) {
            post.setBoard(null);
        }
        posts.clear();
        boardRepository.deleteBoard(findBoard);
        return new DeleteBoardResponse(boardId, 200, "삭제 완료");
    }

    @Override
    public BoardDto findById(Long boardId) {
        Board board = boardRepository.selectBoardById(boardId);
        if (board == null)
            return null;
        return new BoardDto(board);
    }


}
