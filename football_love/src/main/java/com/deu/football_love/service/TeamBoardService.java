package com.deu.football_love.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Post;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamBoard;
import com.deu.football_love.dto.Teamboard.AddTeamBoardRequest;
import com.deu.football_love.dto.Teamboard.AddTeamBoardResponse;
import com.deu.football_love.dto.Teamboard.DeleteTeamBoardResponse;
import com.deu.football_love.dto.Teamboard.TeamBoardDto;
import com.deu.football_love.repository.TeamBoardRepository;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamBoardService {
  private final TeamBoardRepository boardRepository;

  private final TeamRepository teamRepository;

  public AddTeamBoardResponse add(AddTeamBoardRequest request) {
    if (boardRepository.existsByTeamIdAndMemberId(request.getTeamId(), request.getBoardName()))
      throw new IllegalArgumentException("There is already board with the same name.");
    Team findTeam = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new IllegalArgumentException("no such Team data"));
    TeamBoard board = new TeamBoard();
    board.setBoardName(request.getBoardName());
    board.setBoardType(request.getBoardType());
    board.setTeam(findTeam);
    findTeam.getBoards().add(board);
    boardRepository.save(board);

    return AddTeamBoardResponse.from(board);
  }

  public DeleteTeamBoardResponse delete(Long boardId) {
    TeamBoard findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("no such board data"));
    List<Post> posts = findBoard.getPosts();
    for (Post post : posts) {
      post.setBoard(null);
    }
    posts.clear();
    boardRepository.delete(findBoard);
    return new DeleteTeamBoardResponse(boardId, 200, "삭제 완료");
  }

  public TeamBoardDto findById(Long boardId) {
    TeamBoard findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("no such board data"));
    return new TeamBoardDto(findBoard);
  }

  public TeamBoardDto findByTeamIdAndBoardName(Long teamId, String boardName) {
    TeamBoard board =
        boardRepository.findByTeamIdAndBoardName(boardName, teamId).orElseThrow(() -> new IllegalArgumentException("no such board data"));;
    return new TeamBoardDto(board);
  }


}
