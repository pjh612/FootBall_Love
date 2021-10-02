package com.deu.football_love.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {

    @Id
    @GeneratedValue
    @Column(name= "board_id")
    private Long id;

    @Column(name="board_name")
    private String boardName;

    @Column(name="board_type")
    private String boardType;

    @OneToMany(mappedBy = "id")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName ="team_id")
    private Team team;

}
