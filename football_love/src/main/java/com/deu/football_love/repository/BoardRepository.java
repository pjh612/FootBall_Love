package com.deu.football_love.repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.deu.football_love.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
  default boolean existsCommonBoardByBoardName(String boardName) {
    return findWithPagingCommonBoard(boardName, PageRequest.of(0, 1)).size() == 1 ? true : false;
  }

  @Query("SELECT b FROM Board b WHERE b.boardName != :boardName and TYPE(b) != 'TeamBoard'")
  List<Board> findWithPagingCommonBoard(@Param("boardName") String boardName, Pageable pageable);

  @Query("SELECT b FROM Board b WHERE TYPE(b) != 'TeamBoard'")
  List<Board> findCommonBoard();
}
