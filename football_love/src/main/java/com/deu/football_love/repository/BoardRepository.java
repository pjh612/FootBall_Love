package com.deu.football_love.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.deu.football_love.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
  @Query("SELECT count(b)>0 FROM Board b WHERE b.boardName != :boardName")
  boolean existsCommonBoardByBoardName(@Param("boardName") String boardName);

  @Query("SELECT b FROM Board b WHERE TYPE(b) != 'TeamBoard'")
  List<Board> findCommonBoard();
}
