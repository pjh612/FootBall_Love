package com.deu.football_love.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.deu.football_love.domain.TeamBoard;
import com.deu.football_love.domain.type.BoardType;

public interface TeamBoardRepository extends JpaRepository<TeamBoard, Long> {


  @Query("SELECT count(b) FROM TeamBoard b JOIN b.team t WHERE t.id=:teamId AND b.boardType=:boardType")
  int countBoardByType(@Param("teamId") Long teamId, @Param("boardType") BoardType boardType);

  @Query("SELECT b FROM TeamBoard b " + "JOIN FETCH b.team t " + "WHERE t.id = :teamId AND b.boardName = :boardName")
  Optional<TeamBoard> findByTeamIdAndBoardName(@Param("boardName") String boardName, @Param("teamId") Long teamId);

  @Query("SELECT b FROM TeamBoard b JOIN FETCH b.team t WHERE t.id=:teamId AND b.boardName=:boardName")
  List<TeamBoard> findWithPagingByTeamIdAndBoardName(@Param("teamId") Long teamId, @Param("boardName") String boardName, Pageable pageable);

  default boolean existsByTeamIdAndMemberId(Long teamId, String boardName) {
    return findWithPagingByTeamIdAndBoardName(teamId, boardName, PageRequest.of(0, 1)).size() == 1 ? true : false;
  }
}
