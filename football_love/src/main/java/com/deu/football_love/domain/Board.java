package com.deu.football_love.domain;

import com.deu.football_love.domain.type.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team team;

    public Board(String boardName, BoardType boardType) {
        this.boardName = boardName;
        this.boardType = boardType;
    }

    public void deleteBoard() {
        team.getBoards().remove(this);
        this.team = null;
    }
}
