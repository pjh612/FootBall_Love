package com.deu.football_love.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import com.deu.football_love.domain.type.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Board extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "board_id")
  private Long id;

  @Column(name = "board_name")
  private String boardName;

  @Column(name = "board_type")
  private BoardType boardType;

  @OneToMany(mappedBy = "id")
  private List<Post> posts = new ArrayList<>();

  public Board(String boardName, BoardType boardType) {
    this.boardName = boardName;
    this.boardType = boardType;
  }

}
