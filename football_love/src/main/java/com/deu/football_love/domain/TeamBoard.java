package com.deu.football_love.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.deu.football_love.domain.type.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TeamBoard extends Board {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", referencedColumnName = "team_id")
  private Team team;

  public TeamBoard(String boardName, BoardType boardType) {
    super(boardName, boardType);
  }

  public void deleteBoard() {
    team.getBoards().remove(this);
    this.team = null;
  }

}
