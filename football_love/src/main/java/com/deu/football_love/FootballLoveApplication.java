package com.deu.football_love;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.type.BoardType;
import com.deu.football_love.repository.BoardRepository;

@SpringBootApplication
@EnableScheduling
public class FootballLoveApplication {
  BoardRepository boardRepository;

  @Autowired
  FootballLoveApplication(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
    boardRepository.save(new Board("notice", BoardType.NOTICE));
  }

  public static void main(String[] args) {
    // git hub jenkins hook test
    SpringApplication.run(FootballLoveApplication.class, args);
  }
}
