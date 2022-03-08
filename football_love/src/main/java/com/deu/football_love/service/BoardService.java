package com.deu.football_love.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.board.AddBoardRequest;
import com.deu.football_love.dto.board.AddBoardResponse;
import com.deu.football_love.dto.board.DeleteBoardResponse;
import com.deu.football_love.dto.board.QueryBoardDto;
import com.deu.football_love.repository.BoardRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;

  public List<QueryBoardDto> get() {
    List<Board> boardList = boardRepository.findCommonBoard();
    List<QueryBoardDto> queryBoardList = new ArrayList<>();
    for (Board board : boardList) {
      queryBoardList.add(new QueryBoardDto(board));
    }
    return queryBoardList;
  }

  public AddBoardResponse add(AddBoardRequest request) {
    if (boardRepository.existsCommonBoardByBoardName(request.getBoardName())) {
      new IllegalArgumentException("There is a already board with the same name.");
    }
    Board board = new Board();
    board.setBoardName(request.getBoardName());
    board.setBoardType(request.getBoardType());

    boardRepository.save(board);

    return AddBoardResponse.from(board);
  }

  public DeleteBoardResponse delete(Long boardId) {
    Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("no such board data"));
    List<Post> posts = findBoard.getPosts();
    for (Post post : posts) {
      post.setBoard(null);
    }
    posts.clear();
    boardRepository.delete(findBoard);
    return new DeleteBoardResponse(boardId, 200, "삭제 완료");
  }

  public QueryBoardDto findById(Long boardId) {
    Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("no such board data"));
    return new QueryBoardDto(findBoard);
  }
}
