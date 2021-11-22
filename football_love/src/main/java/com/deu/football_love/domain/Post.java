package com.deu.football_love.domain;

import com.deu.football_love.dto.UpdatePostRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Column(name= "post_id")
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_author",referencedColumnName="member_number")
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

    public Post() {
    }

    public Post(Member author, Board board, LocalDateTime createDate, LocalDateTime modifyDate, String title, String content) {
        this.author = author;
        this.board = board;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.title = title;
        this.content = content;
    }


    public void update(UpdatePostRequest request)
    {
        this.setTitle(request.getTitle());
        this.setModifyDate(request.getModifyDate());
        this.setContent(request.getContent());
    }
}
