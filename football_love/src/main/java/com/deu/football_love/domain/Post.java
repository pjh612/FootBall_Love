package com.deu.football_love.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Column(name= "post_id")
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_author",referencedColumnName="member_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Column(name= "post_createdate")
    private LocalDateTime createDate;

    @Column(name= "post_modifydate")
    private LocalDateTime modifyDate;

    @Column(name= "post_title")
    private String title;

    @Column(name= "post_content")
    private String content;


}
