package com.deu.football_love.repository;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.repository.sql.BoardSql;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("SELECT count(b) FROM Board b JOIN b.team t WHERE t.id=:teamId AND b.boardType=:boardType")
    int countBoardByType(@Param("teamId") Long teamId, @Param("boardType") BoardType boardType);

    @Query("SELECT b FROM Board b " +
            "JOIN FETCH b.team t " +
            "WHERE t.id = :teamId AND b.boardName = :boardName")
    Optional<Board> findByTeamIdAndBoardName(@Param("boardName") String boardName, @Param("teamId") Long teamId);

    @Query("SELECT b FROM Board b JOIN FETCH b.team t WHERE t.id=:teamId AND b.boardName=:boardName")
    List<Board> findWithPagingByTeamIdAndBoardName(@Param("teamId") Long teamId, @Param("boardName") String boardName, Pageable pageable);

    default boolean existsByTeamIdAndMemberId(Long teamId,  String boardName)
    {
        return findWithPagingByTeamIdAndBoardName(teamId, boardName, PageRequest.of(0, 1)).size() == 1 ? true : false;
    }
}
